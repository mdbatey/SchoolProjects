package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.UnFollowResponse;
import edu.byu.cs.tweeter.server.dao.AuthInterface;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.FollowInterface;
import edu.byu.cs.tweeter.server.dao.UserInterface;
import edu.byu.cs.tweeter.server.factory.IFactory;
import edu.byu.cs.tweeter.server.factory.TweeterFactory;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends Service{


    private IFactory factory;
    private FollowInterface followDAO;

    private UserInterface userDAO;
    private AuthInterface authDAO;
    public FollowService(TweeterFactory factory) {
        super(factory);
        this.factory = factory;

        followDAO = factory.createFollowDAO();
        userDAO = factory.createUserDAO();
        authDAO = factory.createAuthDAO();


    }

//    private TweeterFactory factory = getTweeterFactory();


    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        if(request.getFollowerAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }

        boolean tokenCheck = authDAO.checkAuthTokenTime(request.getAuthToken().getToken());

        if (!tokenCheck) {
            throw new RuntimeException("[Bad Request] Token is expired");
        }


        return followDAO.getFollowees(request);
//        return getFollowingDAO().getFollowees(request);
    }

    public GetCountResponse getFollweeCount(GetCountRequest request) {
        if(request.getUserAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a user alias");
        } else if(request.getCount() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }

        boolean tokenCheck = authDAO.checkAuthTokenTime(request.getAuthToken().getToken());

        if (!tokenCheck) {
            throw new RuntimeException("[Bad Request] Token is expired");
        }


//        return factory.getFollowDAO().getFolloweeCount(request);

//        return getFollowersDAO().getFolloweeCount(request);

        int count = userDAO.getFollowingCount(request.getUserAlias());

        System.out.println("folloee count: " + count);

        GetCountResponse response = new GetCountResponse(request.getUserAlias(), count);
        return response;
    }




    public FollowersResponse getFollowers(FollowersRequest request) {
        if(request.getFollowerAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }

        boolean tokenCheck = authDAO.checkAuthTokenTime(request.getAuthToken().getToken());

        if (!tokenCheck) {
            throw new RuntimeException("[Bad Request] Token is expired");
        }


        return followDAO.getFollowers(request);
    }


    public GetCountResponse getFollowerCount(GetCountRequest request) {
        if(request.getUserAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a user alias");
        } else if(request.getCount() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }
//        return getFollowersDAO().getFollowerCount(request);
//        return getFollowersDAO().getFollowerCount(request);
//        System.out.println("follower count authtoken: " + request.getAuthToken());
        boolean tokenCheck = authDAO.checkAuthTokenTime(request.getAuthToken().getToken());

        if (!tokenCheck) {
            throw new RuntimeException("[Bad Request] Token is expired");
        }

        int count = userDAO.getFollowersCount(request.getUserAlias());

        GetCountResponse response = new GetCountResponse(request.getUserAlias(), count);
        return response;
    }


    /**
     * Returns an instance of {@link FollowDAO}. Allows mocking of the FollowDAO class
     * for testing purposes. All usages of FollowDAO should get their FollowDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
//    FollowDAO getFollowingDAO() {
//        return new FollowDAO();
//    }
//    FollowDAO getFollowersDAO() {
//        return new FollowDAO();
//    }
//
//    FakeData getFakeData() {
//        return FakeData.getInstance();
//    }

    public FollowResponse follow(FollowRequest request){

        if (request.getCurrentUser().getAlias() == null) {
            throw new RuntimeException("[Bad Request] Missing a username");
        }


        boolean tokenCheck = authDAO.checkAuthTokenTime(request.getAuthToken().getToken());

        if (!tokenCheck) {
            throw new RuntimeException("[Bad Request] Token is expired");
        }

        User user = followDAO.follow(request);

        int followerCount = userDAO.getFollowersCount(request.getSelectedUser().getAlias());
        userDAO.updateFollowersCount(request.getSelectedUser().getAlias(), followerCount + 1);

        int followingCount = userDAO.getFollowingCount(request.getCurrentUser().getAlias());
        userDAO.updateFollowingCount(request.getCurrentUser().getAlias(), followingCount + 1);


        return new FollowResponse(request.getAuthToken(), user);

    }

    public UnFollowResponse unFollow(UnFollowRequest request) {

        if (request.getCurrentUser().getAlias() == null) {
            throw new RuntimeException("[Bad Request] Missing a username");
        }


        boolean tokenCheck = authDAO.checkAuthTokenTime(request.getAuthToken().getToken());

        if (!tokenCheck) {
            throw new RuntimeException("[Bad Request] Token is expired");
        }

        int followerCount = userDAO.getFollowersCount(request.getSelectedUser().getAlias());
        userDAO.updateFollowersCount(request.getSelectedUser().getAlias(), followerCount - 1);
//
        int followingCount = userDAO.getFollowingCount(request.getCurrentUser().getAlias());
        userDAO.updateFollowingCount(request.getCurrentUser().getAlias(), followingCount - 1);


        User user = followDAO.unFollow(request);



        return new UnFollowResponse(request.getAuthToken(), user);
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) {

        if (request.getFollowerAlias() == null) {
            throw new RuntimeException("[Bad Request] Missing a username");
        } else if (request.getFolloweeAlias() == null) {
            throw new RuntimeException("[Bad Request] Missing a username");
        }

        boolean tokenCheck = authDAO.checkAuthTokenTime(request.getAuthToken().getToken());

        if (!tokenCheck) {
            throw new RuntimeException("[Bad Request] Token is expired");
        }


//        boolean isFollowerValue = new Random().nextInt() > 0;
        boolean isFollowerValue = followDAO.isFollower(request);

        return new IsFollowerResponse(request.getAuthToken(), isFollowerValue);
    }




}
