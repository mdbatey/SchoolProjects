package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedStatusTask {

    private static final String LOG_TAG = "GetFeedTask";

    public static final String URL_PATH = "/getfeed";

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected Pair<List<Status>, Boolean> getItems() {
       try {

           List<String> urls = new ArrayList<>();
           urls.add("https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
           List<String> mentions = new ArrayList<>();
           mentions.add("@amy");
           long time = System.currentTimeMillis();
           Status someStatus = new Status("post @amy", getTargetUser(), time, urls, mentions);


           String targerUserAlias = getTargetUser() == null ? null : getTargetUser().getAlias();
//              String lastStatusAlias = getLastItem() == null ? null : getLastItem().getUser().getAlias();
           Status lastStatus = getLastItem() == null ? someStatus : getLastItem();

           FeedRequest request = new FeedRequest(getAuthToken(), targerUserAlias, getLimit(), lastStatus);
           FeedResponse response = getServerFacade().getFeedStatuses(request, URL_PATH);

//              System.out.println("GetFeedTask response Statuses: " + response.getStatuses());
//                System.out.println("GetFeedTask response getHasMorePages: " + response.getHasMorePages());

           if (response.isSuccess()) {
               List<Status> statuses = response.getStatuses();
               boolean hasMorePages = response.getHasMorePages();
               return new Pair<>(statuses, hasMorePages);
           } else {
               sendFailedMessage(response.getMessage());
           }
       } catch (IOException | TweeterRemoteException ex) {
           Log.e(LOG_TAG, "Failed to get Story Statuses", ex);
           sendExceptionMessage(ex);
       }
       return null;



//                return new Pair<>(getFakeData().getFakeStatuses( ), false);
    }
}
