package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class IsFollowerResponse extends Response {


    private boolean isFollower;
    private AuthToken authToken;

    public IsFollowerResponse(String message) {
        super(false, message);
    }

    public IsFollowerResponse( AuthToken authToken, boolean isFollower) {
        super(true, null);
        this.isFollower = isFollower;
        this.authToken = authToken;
    }

    public boolean getIsFollower() {
        return isFollower;
    }

    public void setIsFollower(boolean isFollower) {
        this.isFollower = isFollower;
    }

}
