package edu.byu.cs.tweeter.server.factory;

import edu.byu.cs.tweeter.server.dao.AuthInterface;
import edu.byu.cs.tweeter.server.dao.AuthtokenDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.FollowInterface;
import edu.byu.cs.tweeter.server.dao.StatusDAO;
import edu.byu.cs.tweeter.server.dao.StatusInterface;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.dao.UserInterface;

public class TweeterFactory implements IFactory {

    private FollowDAO followDAO;
    private StatusDAO statusDAO;
    private UserDAO userDAO;
    private AuthtokenDAO authtokenDAO;



    public FollowDAO getFollowDAO() {
        if (followDAO == null) {
            return followDAO = new FollowDAO();
        }
        return followDAO;
    }

    public StatusDAO getStatusDAO() {
        if (statusDAO == null) {
            return statusDAO = new StatusDAO();
        }
        return statusDAO;
    }

    public UserDAO getUserDAO() {
        if (userDAO == null) {
            return userDAO = new UserDAO();
        }
        return userDAO;
    }

    public AuthtokenDAO getAuthtokenDAO() {
        if (authtokenDAO == null) {
            return authtokenDAO = new AuthtokenDAO();
        }
        return authtokenDAO;
    }

    @Override
    public AuthInterface createAuthDAO() {
        return new AuthtokenDAO();
    }

    @Override
    public UserInterface createUserDAO() {
        return new UserDAO();
    }

    @Override
    public FollowInterface createFollowDAO() {
        return new FollowDAO();
    }

    @Override
    public StatusInterface createStatusDAO() {
        return new StatusDAO();
    }
}
