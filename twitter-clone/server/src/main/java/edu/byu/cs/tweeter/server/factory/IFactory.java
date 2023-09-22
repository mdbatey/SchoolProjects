package edu.byu.cs.tweeter.server.factory;

import edu.byu.cs.tweeter.server.dao.AuthInterface;
import edu.byu.cs.tweeter.server.dao.FollowInterface;
import edu.byu.cs.tweeter.server.dao.StatusInterface;
import edu.byu.cs.tweeter.server.dao.UserInterface;

public interface IFactory {

    public AuthInterface createAuthDAO();
    public UserInterface createUserDAO();
    public FollowInterface createFollowDAO();
    public StatusInterface createStatusDAO();

}
