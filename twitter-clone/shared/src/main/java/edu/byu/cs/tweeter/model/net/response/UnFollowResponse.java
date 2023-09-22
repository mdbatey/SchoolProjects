package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UnFollowResponse extends Response{

    private AuthToken authToken;
    private User user;

    public UnFollowResponse(String message) {
        super(false, message);
    }

    public UnFollowResponse(AuthToken authToken, User user) {
        super(true, null);
        this.authToken = authToken;
        this.user = user;
    }
}
