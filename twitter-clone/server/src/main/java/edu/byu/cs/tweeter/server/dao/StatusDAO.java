package edu.byu.cs.tweeter.server.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.factory.DataPage;
import edu.byu.cs.tweeter.server.factory.FeedModel;
import edu.byu.cs.tweeter.server.factory.StoryModel;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;


public class StatusDAO extends DynamoDAO implements StatusInterface {

    private static final String StoryTableName = "Story";
    private static final String FeedTableName = "Feed";


    private static final String author_alias = "author_alias";
    private static final String timestamp = "timestamp";



    // DynamoDB client
    private static DynamoDbClient dynamoDbClient = getDynamoDbClient();

    private static DynamoDbEnhancedClient enhancedClient = getEnhancedClient();



    // given a visitor where have they visited
    // given a follower who is there followee
    public DataPage<Status> getStoryStatuses(String alias, int pageSize, Status lastStatus) {
        DynamoDbTable<StoryModel> table = enhancedClient.table(StoryTableName, TableSchema.fromBean(StoryModel.class));
        Key key = Key.builder()
                .partitionValue(alias)
                .build();

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .limit(pageSize).scanIndexForward(false);


        if(alias != null){
            // Build up the Exclusive Start Key (telling DynamoDB where you left off reading items)
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(author_alias, AttributeValue.builder().s(alias).build());

            String lastTimestamp = lastStatus.getTimestamp().toString();
            startKey.put(timestamp, AttributeValue.builder().n(lastTimestamp).build());

            requestBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();

        DataPage<Status> result = new DataPage<Status>();

        PageIterable<StoryModel> pages = table.query(request);
        pages.stream()
                .limit(1)
                .forEach((Page<StoryModel> page) -> {
                    result.setHasMorePages(page.lastEvaluatedKey() != null);
                    page.items().forEach(status -> result.getValues().add(convertStoryModelToStatus(status)));
                });

//        System.out.println("result: " + result.getValues());
        return result;
    }



    private Status convertStoryModelToStatus (StoryModel model) {
//        Status(String post, User user, Long timestamp, List<String> urls, List<String> mentions)
        return new Status(model.getPost(), getUserFromAlias(model.getAuthor_alias()), model.getTimestamp(), model.getUrls(), model.getMentions());
    }

    private User getUserFromAlias (String alias) {
        UserDAO userDAO = new UserDAO();
        return userDAO.getUser(alias);
    }


    public DataPage<Status> getFeedStatuses(String alias, int pageSize, Status lastStatus) {
        DynamoDbTable<FeedModel> table = enhancedClient.table(FeedTableName, TableSchema.fromBean(FeedModel.class));
        Key key = Key.builder()
                .partitionValue(alias)
                .build();

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .limit(pageSize).scanIndexForward(false);


        if(alias != null){
            // Build up the Exclusive Start Key (telling DynamoDB where you left off reading items)
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put("alias", AttributeValue.builder().s(alias).build());

            String lastTimestamp = lastStatus.getTimestamp().toString();
            startKey.put(timestamp, AttributeValue.builder().n(lastTimestamp).build());

            requestBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();

        DataPage<Status> result = new DataPage<Status>();

        PageIterable<FeedModel> pages = table.query(request);
        pages.stream()
                .limit(1)
                .forEach((Page<FeedModel> page) -> {
                    result.setHasMorePages(page.lastEvaluatedKey() != null);
                    page.items().forEach(status -> result.getValues().add(convertFeedModelToStatus(status)));
                });

//        System.out.println("result: " + result.getValues());
        return result;
    }

    private Status convertFeedModelToStatus (FeedModel model) {
//        Status(String post, User user, Long timestamp, List<String> urls, List<String> mentions)
        return new Status(model.getPost(), getUserFromAlias(model.getAuthor_alias()), model.getTimestamp(), model.getUrls(), model.getMentions());
    }


//    public List<Status> getStoryStatuses(String alias){
//        DynamoDbTable<StoryModel> table = enhancedClient.table(StoryTableName, TableSchema.fromBean(StoryModel.class));
//        Key key = Key.builder()
//                .partitionValue(alias)
//                .build();
//
//        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
//                .queryConditional(QueryConditional.keyEqualTo(key))
//                .scanIndexForward(false)
//                .build();
//
//        List<Status> result = new ArrayList<>();
//
//        PageIterable<StoryModel> pages = table.query(request);
//        pages.stream()
//                .forEach((Page<StoryModel> page) -> {
//                    page.items().forEach(status -> result.add(convertStoryModelToStatus(status)));
//                });
//
//        System.out.println("story result: " + result);
//        return result;
//    }


    public PostStatusResponse postStatus(PostStatusRequest request){

        postStatusToStory(request);


//        postStatusToFeed(request, request.getUserAlias(), currentTime);

        FollowDAO followDao = new FollowDAO();
        List<User> allFollowers = followDao.getAllFollowers(request.getUserAlias());
        System.out.println("allFollowers: " + allFollowers);

        for(User follower : allFollowers){
            postStatusToFeed(request, follower.getAlias());
        }

        return new PostStatusResponse(request.getAuthtoken(), request.getStatus());

    }

    public void postStatusToStory(PostStatusRequest request) {

        DynamoDbTable<StoryModel> table = enhancedClient.table(StoryTableName, TableSchema.fromBean(StoryModel.class));
//        Key key = Key.builder()
//                .partitionValue(request.getUserAlias())
//                .build();

        System.out.println("request: " + request.getStatus());


        StoryModel model = new StoryModel();
        model.setAuthor_alias(request.getUserAlias());
        model.setPost(request.getStatus().getPost());
        model.setTimestamp(request.getStatus().getTimestamp());
        model.setUrls(request.getStatus().getUrls());
        model.setMentions(request.getStatus().getMentions());

        table.putItem(model);



    }

    public void postStatusToFeed(PostStatusRequest request, String userAlias) {

        // get followers
        // add a feed item for each follower.

        DynamoDbTable<FeedModel> table = enhancedClient.table(FeedTableName, TableSchema.fromBean(FeedModel.class));

        FeedModel model = new FeedModel();
        model.setAuthor_alias(request.getUserAlias());
        model.setPost(request.getStatus().getPost());
        model.setTimestamp(request.getStatus().getTimestamp());
        model.setUrls(request.getStatus().getUrls());
        model.setMentions(request.getStatus().getMentions());
        model.setAlias(userAlias);

        table.putItem(model);

    }







    ////////////////////////////////////////////////////////
    public StoryResponse getStoryStatues(StoryRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getStatusAlias() != null;

//        List<Status> allStoryStatuses = getDummyStoryStatuses();

        DataPage<Status> pageStatuses = getStoryStatuses(request.getStatusAlias(), request.getLimit(), request.getLastStatus());
//        List<Status> allStoryStatuses = getStoryStatuses(request.getStatusAlias(), request.getLimit(), request.getLastStatus());
//        List<Status> allStoryStatuses = getStoryStatuses(request.getStatusAlias());
//        List<Status> responseStoryStatuses = new ArrayList<>(request.getLimit());
//
//        System.out.println("allStoryStatuses ");
////        System.out.println("other status " + otherStoryStatuses);
//
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            if (allStoryStatuses != null) {
//                int statusIndex = getStoryStatusStartingIndex(request.getLastStatus().getTimestamp(), allStoryStatuses);
////                int statusIndex = getStatusStartingIndex(request.getLastStatus().getUser().getAlias(), allStoryStatuses);
////                int statusIndex = 0;
//
////                System.out.println("story statusIndex: " + statusIndex);
//
//                for(int limitCounter = 0; statusIndex < allStoryStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
//                    responseStoryStatuses.add(allStoryStatuses.get(statusIndex));
//                }
//
//                hasMorePages = statusIndex < allStoryStatuses.size();
//            }
//        }
        StoryResponse storyResponse = new StoryResponse(pageStatuses.getValues(), pageStatuses.isHasMorePages());

//        System.out.println("storyResponse is successful: " + storyResponse.isSuccess());
        return storyResponse;
//        return new StoryResponse(allStoryStatuses, hasMorePages);
    }

//    private int getStoryStatusStartingIndex(long lastStatusTimestamp, List<Status> allStatuses) {
//
//        int StatusIndex = 0;
//
//        if(Objects.isNull(lastStatusTimestamp)) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allStatuses.size(); i++) {
//                if(lastStatusTimestamp == allStatuses.get(i).getTimestamp()) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    StatusIndex = i + 1;
//                    break;
//                }
//            }
//        }
//
//        return StatusIndex;
//    }


//    private int getStatusStartingIndex(String lastStatusAlias, List<Status> allStatuses) {
//
//        int StatusIndex = 0;
//
//        if(lastStatusAlias != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allStatuses.size(); i++) {
//                if(lastStatusAlias.equals(allStatuses.get(i).getUser().getAlias())) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    StatusIndex = i + 1;
//                    break;
//                }
//            }
//        }
//
//        return StatusIndex;
//    }


//    List<Status> getDummyStoryStatuses() {
//        return getFakeData().getFakeStatuses();
//    }
//
//    FakeData getFakeData() {
//        return FakeData.getInstance();
//    }
//
//    List<Status> getDummyFeedStatuses() {
//        return getFakeData().getFakeStatuses();
//    }

    public FeedResponse getFeedStatues(FeedRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getStatusAlias() != null;

//        List<Status> allFeedStatuses = getDummyFeedStatuses();
        DataPage<Status> pageStatuses = getFeedStatuses(request.getStatusAlias(), request.getLimit(), request.getLastStatus());

//        List<Status> responseFeedStatuses = new ArrayList<>(request.getLimit());
//        System.out.println("FeedRequest: " + request.getLimit());
//        System.out.println("FeedRequest: " + request.getStatusAlias());
//        System.out.println("FeedRequest: " + request.getLastStatusAlias());
//        System.out.println("FeedRequest: " + request.getAuthToken());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            if (allFeedStatuses != null) {
//                int statusIndex = getStatusStartingIndex(request.getLastStatusAlias(), allFeedStatuses);
//                System.out.println("feed statusIndex: " + statusIndex);
//
//                for(int limitCounter = 0; statusIndex < allFeedStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
//                    responseFeedStatuses.add(allFeedStatuses.get(statusIndex));
//                }
//
//
//
//                hasMorePages = statusIndex < allFeedStatuses.size();
//            }
//        }

        return new FeedResponse(pageStatuses.getValues(), pageStatuses.isHasMorePages());
    }



//    public Integer getFollowerCount(User follower) {
//        // TODO: uses the dummy data.  Replace with a real implementation.
//        assert follower != null;
//        return getDummyStoryStatuses().size();
//    }

}
