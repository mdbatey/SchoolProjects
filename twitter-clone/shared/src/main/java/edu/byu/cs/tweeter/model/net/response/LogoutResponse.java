package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogoutResponse extends Response{

    private AuthToken authToken;

    public LogoutResponse(String message) {
        super(false, message);
    }

    public LogoutResponse(AuthToken authToken) {
        super(true, null);
        this.authToken = authToken;
    }
}
