package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class UserRequest {

    private AuthToken authToken;
    private String username;


    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private UserRequest() {}


    public UserRequest(AuthToken authToken, String username) {
        this.authToken = authToken;
        this.username = username;

    }

    /**
     * Returns the auth token of the user who is making the request.
     *
     * @return the auth token.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }

    /**
     * Sets the auth token.
     *
     * @param authToken the auth token.
     */
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the follower.
     *
     * @param username the follower.
     */
    public void setUsername(String username) {
        this.username = username;
    }


}
