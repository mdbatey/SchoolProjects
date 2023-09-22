package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowResponse extends Response{

    private AuthToken authToken;
    private User user;

    public FollowResponse(String message) {
        super(false, message);
    }

    public FollowResponse(AuthToken authToken, User user) {
        super(true, null);
        this.authToken = authToken;
        this.user = user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public User getUser() {
        return user;
    }


}
