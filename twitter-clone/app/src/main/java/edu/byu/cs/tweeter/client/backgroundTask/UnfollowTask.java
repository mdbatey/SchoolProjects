package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.model.net.response.UnFollowResponse;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends AuthenticatedTask {

    private static final String LOG_TAG = "UnFollowTask";

    public static final String URL_PATH = "/unfollow";

    /**
     * The user that is being followed.
     */
    private final User selectedUser;
    private final User currentUser;

    public UnfollowTask(AuthToken authToken, User currentUser, User selectedUser, Handler messageHandler) {
        super(authToken, messageHandler);
        this.selectedUser = selectedUser;
        this.currentUser = currentUser;
    }

    @Override
    protected void runTask() {
        // We could do this from the presenter, without a task and handler, but we will
        // eventually access the database from here when we aren't using dummy data.

        try {
            UnFollowRequest request = new UnFollowRequest(getAuthToken(), currentUser, selectedUser);
            UnFollowResponse response = getServerFacade().unFollow(request, URL_PATH);

            if (response.isSuccess()) {
                sendSuccessMessage();
            } else {
                sendFailedMessage(response.getMessage());
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }

        // Call sendSuccessMessage if successful
//        sendSuccessMessage();
        // or call sendFailedMessage if not successful
        // sendFailedMessage()
    }


}
