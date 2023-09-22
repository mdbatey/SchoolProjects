package edu.byu.cs.tweeter.server.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.server.factory.DataPage;
import edu.byu.cs.tweeter.server.factory.Follows;
import edu.byu.cs.tweeter.util.FakeData;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;


/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowDAO extends DynamoDAO implements FollowInterface {



    private static final String TableName = "follows";
    public static final String IndexName = "follows_index";

    private static final String follower_handle = "follower_handle";
    private static final String followee_handle = "followee_handle";



    // DynamoDB client
    private static DynamoDbClient dynamoDbClient = getDynamoDbClient();

    private static DynamoDbEnhancedClient enhancedClient = getEnhancedClient();




//    public void recordFollows(String follower, String followee, String follower_name, String followee_name) {
//        DynamoDbTable<Follows> table = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class));
//        Key key = Key.builder()
//                .partitionValue(follower).sortValue(followee)
//                .build();
//
//        // load it if it exists
//        Follows follows = table.getItem(key);
//        if(follows != null) {
////            follows.setFollower_name(follows.getFollower_name());
//            follows.setFollower_firstName(follows.getFollower_firstName());
//            follows.setFollower_lastName(follows.getFollower_lastName());
//            follows.setFollower_imageUrl(follows.getFollower_imageUrl());
////            follows.setFollowee_name(follows.getFollowee_name());
//            follows.setFollowee_firstName(follows.getFollowee_firstName());
//            follows.setFollowee_lastName(follows.getFollowee_lastName());
//            follows.setFollowee_imageUrl(follows.getFollowee_imageUrl());
//            table.updateItem(follows);
//        } else {
//            Follows newFollows = new Follows();
//            newFollows.setFollower_handle(follower);
//            newFollows.setFollowee_handle(followee);
////            newFollows.setFollower_name(follower_name);
//            newFollows.setFollower_firstName(follower_name);
//            newFollows.setFollower_lastName(follower_name);
//            newFollows.setFollower_imageUrl(follower_name);
////            newFollows.setFollowee_name(followee_name);
//            newFollows.setFollowee_firstName(followee_name);
//            newFollows.setFollowee_lastName(followee_name);
//            newFollows.setFollowee_imageUrl(followee_name);
//            table.putItem(newFollows);
//        }
//    }


    public boolean isFollower(IsFollowerRequest request) {
        DynamoDbTable<Follows> table = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class));
        Key key = Key.builder()
                .partitionValue(request.getFollowerAlias()).sortValue(request.getFolloweeAlias())
                .build();

        Follows follows = table.getItem(key);

        if (follows == null) {
            return false;
        }

        return true;
    }


    public User follow(FollowRequest request){
        DynamoDbTable<Follows> table = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class));
        Key key = Key.builder()
                .partitionValue(request.getCurrentUser().getAlias()).sortValue(request.getSelectedUser().getAlias())
                .build();

//        Follows follows = table.getItem(key);

        Follows newFollows = new Follows();
        newFollows.setFollower_handle(request.getCurrentUser().getAlias());
        newFollows.setFollower_firstName(request.getCurrentUser().getFirstName());
        newFollows.setFollower_lastName(request.getCurrentUser().getLastName());
        newFollows.setFollower_imageUrl(request.getCurrentUser().getImageUrl());


        newFollows.setFollowee_handle(request.getSelectedUser().getAlias());
        newFollows.setFollowee_firstName(request.getSelectedUser().getFirstName());
        newFollows.setFollowee_lastName(request.getSelectedUser().getLastName());
        newFollows.setFollowee_imageUrl(request.getSelectedUser().getImageUrl());

        table.updateItem(newFollows);

        return new User(newFollows.getFollowee_firstName(), newFollows.getFollowee_lastName(), newFollows.getFollowee_imageUrl(), newFollows.getFollowee_handle());
    }

    public User unFollow(UnFollowRequest request){
        DynamoDbTable<Follows> table = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class));
        Key key = Key.builder()
                .partitionValue(request.getCurrentUser().getAlias()).sortValue(request.getSelectedUser().getAlias())
                .build();

        Follows follows = table.getItem(key);

        table.deleteItem(key);

        return new User(follows.getFollowee_firstName(), follows.getFollowee_lastName(), follows.getFollowee_imageUrl(), follows.getFollowee_handle());
    }


//    public int getFolloweeSize(GetCountRequest countRequest){
//
//        DynamoDbIndex<Follows> index = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class)).index(IndexName);
//        Key key = Key.builder()
//                .partitionValue(countRequest.getUserAlias())
//                .build();
//
////        QueryConditional queryConditional = QueryConditional.sortBeginsWith();
//
//        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
//                .queryConditional(QueryConditional.keyEqualTo(key))
//                .scanIndexForward(true);
//
//
//
//        QueryEnhancedRequest request = requestBuilder.build();
//
////        index.query(request).items().size();
////        Long itemCount = index.query(request).stream().count();
//
//
//
//
//        int count = 0;
//        for (Page<Follows> page : index.query(request)) {
//
////            count++;
//            count = page.items().size();
//            System.out.println("page size : " + page.items().size());
//            System.out.println("page items : " + page.items());
//        }
//
//
//        System.out.println("count: " + count);
//
//        return count;
//
//    }
//
//    public int getFollowerSize(GetCountRequest countRequest){
//
//        DynamoDbIndex<Follows> table = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class)).index(IndexName);
//        Key key = Key.builder()
//                .partitionValue(countRequest.getUserAlias())
//                .build();
//
//        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
//                .queryConditional(QueryConditional.keyEqualTo(key))
//                .scanIndexForward(true);
//
//        QueryEnhancedRequest request = requestBuilder.build();
//
//
//
////        index.query(request).items().size();
////        Long itemCount = index.query(request).stream().count();
//
//
//
//
//        int count = 0;
//        for (Page<Follows> page : table.query(request)) {
//
////            count++;
//            count = page.items().size();
//            System.out.println("page size : " + page.items().size());
//            System.out.println("page items : " + page.items());
//        }
//
//
//        System.out.println("count: " + count);
//
//        return count;
//
//
//
//    }
//
//        DynamoDbIndex<Follows> index = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class)).index(IndexName);
//        Key key = Key.builder()
//                .partitionValue(countRequest.getUserAlias())
//                .build();
//
//        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
//                .queryConditional(QueryConditional.keyEqualTo(key))
//                .scanIndexForward(true);
//
////        if(isNonEmptyString(lastFollower)) {
//            Map<String, AttributeValue> startKey = new HashMap<>();
//            startKey.put(followee_handle, AttributeValue.builder().s(countRequest.getUserAlias()).build());
////            startKey.put(follower_handle, AttributeValue.builder().s(lastFollower).build());
//
//            requestBuilder.exclusiveStartKey(startKey);
////        }
//
//        QueryEnhancedRequest request = requestBuilder.build();
//
//        DataPage<Follows> result = new DataPage<Follows>();
//
//        int count = 0;
//
//        SdkIterable<Page<Follows>> sdkIterable = index.query(request);
//        PageIterable<Follows> pages = PageIterable.create(sdkIterable);
//        pages.stream()
//                .limit(1)
//                .forEach((Page<Follows> page) -> {
//                    result.setHasMorePages(page.lastEvaluatedKey() != null);
//                    page.items().forEach(follow -> result.getValues().add(follow));
//                });
//
//        return result.getValues().size();
//
//
//public int getFollwerFollloweeCount(GetCountRequest countRequest){
//    DynamoDbTable<Follows> table = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class));
//    Key key = Key.builder()
//            .partitionValue(countRequest.getUserAlias())
//            .build();
//
//    QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
//            .queryConditional(QueryConditional.keyEqualTo(key))
//            .scanIndexForward(true);
//
//    if(isNonEmptyString(countRequest.getUserAlias())) {
//        // Build up the Exclusive Start Key (telling DynamoDB where you left off reading items)
//        Map<String, AttributeValue> startKey = new HashMap<>();
////        startKey.put(follower_handle, AttributeValue.builder().s(countRequest.getUserAlias()).build());
//        startKey.put(followee_handle, AttributeValue.builder().s(countRequest.getUserAlias()).build());
//
//        requestBuilder.exclusiveStartKey(startKey);
//    }
//
//    QueryEnhancedRequest request = requestBuilder.build();
//
//    DataPage<User> result = new DataPage<User>();
//
//    PageIterable<Follows> pages = table.query(request);
//    int count = 0;
//    for(Page<Follows> page : table.query(request)){
//        count = page.items().size();
////        System.out.println("page size : " + page.items().size());
////        System.out.println("page items : " + page.items());
//    }
//
////    pages.stream()
////            .limit(1)
////            .forEach((Page<Follows> page) -> {
////                result.setHasMorePages(page.lastEvaluatedKey() != null);
////                page.items().forEach(follow -> result.getValues().add(convertFollowToFollowee(follow)));
////            });
//
//
//    return count;
//}



    // given a visitor where have they visited
    // given a follower who is there followee
    public DataPage<User> getFollowerFollowee(String follower, int pageSize, String lastFollowee) {
        DynamoDbTable<Follows> table = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class));
        Key key = Key.builder()
                .partitionValue(follower)
                .build();

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .limit(pageSize).scanIndexForward(true);

        if(isNonEmptyString(lastFollowee)) {
            // Build up the Exclusive Start Key (telling DynamoDB where you left off reading items)
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(follower_handle, AttributeValue.builder().s(follower).build());
            startKey.put(followee_handle, AttributeValue.builder().s(lastFollowee).build());

            requestBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();

        DataPage<User> result = new DataPage<User>();

//        AtomicInteger count = new AtomicInteger();

        PageIterable<Follows> pages = table.query(request);
        pages.stream()
                .limit(1)
                .forEach((Page<Follows> page) -> {
                    result.setHasMorePages(page.lastEvaluatedKey() != null);
                    page.items().forEach(follow -> {
                        User temp = convertFollowToFollowee(follow);
                        result.getValues().add(temp);
                        if (temp.getAlias().equals(lastFollowee)){
                            result.setHasMorePages(true);
                        }
                    });
                });



        return result;
    }

    // given a location who was a visitor
    // given a followee who is there follower
    public DataPage<User>  getFollowers(String followee, int pageSize, String lastFollower) {
        DynamoDbIndex<Follows> index = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class)).index(IndexName);
        Key key = Key.builder()
                .partitionValue(followee)
                .build();

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .limit(pageSize).scanIndexForward(true);

        if(isNonEmptyString(lastFollower)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(followee_handle, AttributeValue.builder().s(followee).build());
            startKey.put(follower_handle, AttributeValue.builder().s(lastFollower).build());

            requestBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = requestBuilder.build();

        DataPage<User> result = new DataPage<User>();

        SdkIterable<Page<Follows>> sdkIterable = index.query(request);
        PageIterable<Follows> pages = PageIterable.create(sdkIterable);
        pages.stream()
                .limit(1)
                .forEach((Page<Follows> page) -> {
                    result.setHasMorePages(page.lastEvaluatedKey() != null);
                    page.items().forEach(follow -> result.getValues().add(convertFollowToFollower(follow)));
                });

        return result;
    }

    public List<User> getAllFollowers(String userAlias){
        DynamoDbTable<Follows> table = enhancedClient.table(TableName, TableSchema.fromBean(Follows.class));
        Key key = Key.builder()
                .partitionValue(userAlias)
                .build();

        QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .scanIndexForward(true);

        QueryEnhancedRequest request = requestBuilder.build();

        DataPage<User> result = new DataPage<User>();

        PageIterable<Follows> pages = table.query(request);
        pages.stream()
                .forEach((Page<Follows> page) -> {
                    result.setHasMorePages(page.lastEvaluatedKey() != null);
                    page.items().forEach(follow -> result.getValues().add(convertFollowToFollowee(follow)));
                });

        return result.getValues();
    }

    public User convertFollowToFollower(Follows follows) {
        User user = new User(follows.getFollower_firstName(), follows.getFollower_lastName(),
                follows.getFollower_handle(), follows.getFollower_imageUrl());
//        user.setAlias(follows.getFollower_handle());
//        user.setFirstName(follows.getFollower_firstName());
//        user.setLastName(follows.getFollower_lastName());
//        user.setImageUrl(follows.getFollower_imageUrl());
        return user;
    }

    public User convertFollowToFollowee(Follows follows) {
        User user = new User(follows.getFollowee_firstName(), follows.getFollowee_lastName(),
                follows.getFollowee_handle(), follows.getFollowee_imageUrl());

        return user;
    }


    ///////////////////////////////////////////////////////////////

    /**
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @
     * param followee the User whose count of how many following is desired.
     * @return said count.
     */
//    public Integer getFolloweeCount(User follower) {
//        // TODO: uses the dummy data.  Replace with a real implementation.
//        assert follower != null;
//        return getDummyFollowees().size();


//    public GetCountResponse getFolloweeCount(GetCountRequest request) {
//
//        assert request.getUserAlias() != null;
////        Integer count = getDummyFollowers().size();
//        Integer count = getFollwerFollloweeCount(request);
//        return new GetCountResponse(request.getUserAlias(), count);
//
//    }

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getFollowerAlias() != null;

        DataPage<User> pageFollowees = getFollowerFollowee(request.getFollowerAlias(), request.getLimit(), request.getLastFolloweeAlias());

        return new FollowingResponse(pageFollowees.getValues(), pageFollowees.isHasMorePages());


//        List<User> allFollowees = getDummyFollowees();

//        List<User> allFollowees = getFollowerFollowee(request.getFollowerAlias(), request.getLimit(), request.getLastFolloweeAlias());
//        List<User> responseFollowees = new ArrayList<>(request.getLimit());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            if (allFollowees != null) {
//                int followeesIndex = getFolloweesStartingIndex(request.getLastFolloweeAlias(), allFollowees);
//
//                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
//                    responseFollowees.add(allFollowees.get(followeesIndex));
//                }
//
//                hasMorePages = followeesIndex < allFollowees.size();
//            }
//        }
//
//        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFolloweeAlias.equals(allFollowees.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyFollowees() {
        return FakeData.getInstance().getFakeUsers();
    }

    /**
     * Returns the {@link FakeData} object used to generate dummy followees.
     * This is written as a separate method to allow mocking of the {@link FakeData}.
     *
     * @return a {@link FakeData} instance.
     */
//    FakeData getFakeData() {
//        return FakeData.getInstance();
//    }



//    public Integer getFollowerCount(GetCountRequest request) {
//        User follower
//
//        // TODO: uses the dummy data.  Replace with a real implementation.
//        assert follower != null;
//        return getDummyFollowers().size();
    //}
//      public GetCountResponse getFollowerCount(GetCountRequest request) {
//
//        assert request.getUserAlias() != null;
////        Integer count = getDummyFollowers().size();
//        Integer count = getFollowerSize(request);
//        return new GetCountResponse(request.getUserAlias(), count);
//
//    }

    /**
     * Gets the users from the database that the user specified in the request is following. Uses
     * information in the request object to limit the number of followees returned and to return the
     * next set of followees after any that were returned in a previous request. The current
     * implementation returns generated data and doesn't actually access a database.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowersResponse getFollowers(FollowersRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getFollowerAlias() != null;

        DataPage<User> pageFollowers = getFollowers(request.getFollowerAlias(), request.getLimit(), request.getLastFollowerAlias());

        return new FollowersResponse(pageFollowers.getValues(), pageFollowers.isHasMorePages());



////        List<User> allFollowers = getDummyFollowees();
//        List<User> allFollowers = getFollowers(request.getFollowerAlias(), request.getLimit(), request.getLastFollowerAlias());
//        List<User> responseFollowees = new ArrayList<>(request.getLimit());
////        System.out.println("allFollowees : " + allFollowers);
////        System.out.println("request.getLimit() : " + request.getLimit());
////        System.out.println("request.getLastFollowerAlias() : " + request.getLastFollowerAlias());
////        System.out.println("request.getFollowerAlias() : " + request.getFollowerAlias());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            if (allFollowers != null) {
//                int followersIndex = getFolloweesStartingIndex(request.getLastFollowerAlias(), allFollowers);
////                System.out.println("followeesIndex : " + followersIndex);
//
//                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
//                    responseFollowees.add(allFollowers.get(followersIndex));
//                }
//
//                hasMorePages = followersIndex < allFollowers.size();
//            }
//        }
//
//        return new FollowersResponse(responseFollowees, hasMorePages);
    }

//    private int getFollowersStartingIndex(String lastFollowerAlias, List<User> allFollowers) {
//
//        int followersIndex = 0;
//
//        if(lastFollowerAlias != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allFollowers.size(); i++) {
//                if(lastFollowerAlias.equals(allFollowers.get(i).getAlias())) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    followersIndex = i + 1;
//                    break;
//                }
//            }
//        }
//
//        return followersIndex;
//    }
//
//
//    public FollowersResponse getFollowers(FollowersRequest request) {
//        // TODO: Generates dummy data. Replace with a real implementation.
//        assert request.getLimit() > 0;
//        assert request.getFollowerAlias() != null;
//
//        List<User> allFollowers = getDummyFollowers();
////        List<User> allFollowers = getFollowers(request.getFollowerAlias(), request.getLimit(), request.getLastFollowerAlias());
//        List<User> responseFollowers = new ArrayList<>(request.getLimit());
//        System.out.println("followers allFollowers : " + allFollowers);
//        System.out.println("followers request.getLimit() : " + request.getLimit());
//        System.out.println("followers request.getLastFollowerAlias() : " + request.getLastFollowerAlias());
//        System.out.println("followers request.getFollowerAlias() : " + request.getFollowerAlias());
//
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            if (allFollowers != null) {
//                int followersIndex = getFollowersStartingIndex(request.getLastFollowerAlias(), allFollowers);
//                System.out.println("followers followersIndex : " + followersIndex);
//
//                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
//                    responseFollowers.add(allFollowers.get(followersIndex));
//                }
//
//                hasMorePages = followersIndex < allFollowers.size();
//            }
//        }
//
//        return new FollowersResponse(responseFollowers, hasMorePages);
//    }
//
//
//    private int getFollowersStartingIndex(String lastFollowerAlias, List<User> allFollowers) {
//
//        int followersIndex = 0;
//
//        if(lastFollowerAlias != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allFollowers.size(); i++) {
//                if(lastFollowerAlias.equals(allFollowers.get(i).getAlias())) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    followersIndex = i + 1;
//                    break;
//                }
//            }
//        }
//
//        return followersIndex;
//    }


//    List<User> getDummyFollowers() {
//        return getFakeData().getFakeUsers();
//    }


}
