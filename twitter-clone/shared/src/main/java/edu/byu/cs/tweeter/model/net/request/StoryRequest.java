package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;


public class StoryRequest {

    private AuthToken authToken;
    private String statusAlias;
    private int limit;

    private Status lastStatus;
//    private String lastStatusAlias;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private StoryRequest() {}

    public StoryRequest(AuthToken authToken, String statusAlias, int limit, Status lastStatus) {
        this.authToken = authToken;
        this.statusAlias = statusAlias;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getStatusAlias() {
        return statusAlias;
    }

    public void setStatusAlias(String statusAlias) {
        this.statusAlias = statusAlias;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }
}
