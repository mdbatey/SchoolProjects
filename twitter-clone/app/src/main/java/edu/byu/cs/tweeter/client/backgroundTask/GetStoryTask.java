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
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that retrieves a page of statuses from a user's story.
 */
public class GetStoryTask extends PagedStatusTask {

    private static final String LOG_TAG = "GetStoryTask";

    public static final String URL_PATH = "/getstory";

    public GetStoryTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
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

            String targetUserAlias = getTargetUser() == null ? null : getTargetUser().getAlias();
            Status lastStatus = getLastItem() == null ? someStatus : getLastItem();
//            String lastStatusAlias = getLastItem() == null ? "temp" : getLastItem().getUser().getAlias();



//            System.out.println("GetStoryTask targetUserAlias: " + targetUserAlias);
//            System.out.println("GetStoryTask lastStatus: " + lastStatus);
//            System.out.println("GetStoryTask getLimit(): " + getLimit());
//            System.out.println("GetStoryTask getAuthToken(): " + getAuthToken());


            StoryRequest request = new StoryRequest(getAuthToken(), targetUserAlias, getLimit(), lastStatus);
            StoryResponse response = getServerFacade().getStoryStatuses(request, URL_PATH);

//            System.out.println("GetStoryTask response statuses: " + response.getStatuses());
//            System.out.println("GetStoryTask response getHasMorePages: " + response.getHasMorePages());
//            System.out.println("GetStoryTask isSuccess: " + response.isSuccess());
//            System.out.println("GetStoryTask response statuses: " + response.getStatuses());
            if (response.isSuccess()) {
                List<Status> statuses = response.getStatuses();
                boolean hasMorePages = response.getHasMorePages();
//                System.out.println("GetStoryTask hasMorePages: " + hasMorePages);
                return new Pair<>(statuses, hasMorePages);

            } else {
                sendFailedMessage(response.getMessage());
            }

        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to get Story Statuses", ex);
            sendExceptionMessage(ex);
        }
        return null;
//        System.out.println("GetStoryTask: " + getFakeData().getPageOfStatus(getLastItem(), getLimit()));
//          return getFakeData().getPageOfStatus(getLastItem(), getLimit());
    }


}
