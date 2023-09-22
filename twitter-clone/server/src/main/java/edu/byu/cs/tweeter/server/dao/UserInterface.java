package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;

public interface UserInterface {
    boolean loginUser(LoginRequest request);

    User getUser(String username);

    void registerNewUser(RegisterRequest request);

    int getFollowingCount(String userAlias);

    int getFollowersCount(String userAlias);

    void updateFollowersCount(String alias, int i);

    void updateFollowingCount(String alias, int i);
}
