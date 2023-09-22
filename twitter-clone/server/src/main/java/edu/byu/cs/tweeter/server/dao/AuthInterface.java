package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public interface AuthInterface {
    AuthToken createAuthToken(String username);

    void logoutUser(String token);

    boolean checkAuthTokenTime(String token);
}
