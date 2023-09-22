package edu.byu.cs.tweeter.model.net.request;


import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetCountRequest {

    private String userAlias;
    private int count;
    private AuthToken authToken;

    public GetCountRequest () {}

    public GetCountRequest(AuthToken authToken, String userAlias, int count) {
        this.userAlias = userAlias;
        this.count = count;
        this.authToken = authToken;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
