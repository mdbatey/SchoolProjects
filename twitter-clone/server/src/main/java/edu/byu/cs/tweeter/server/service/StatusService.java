package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.AuthInterface;
import edu.byu.cs.tweeter.server.dao.StatusInterface;
import edu.byu.cs.tweeter.server.factory.IFactory;
import edu.byu.cs.tweeter.server.factory.TweeterFactory;


public class StatusService extends Service{


    private IFactory factory;

    private StatusInterface statusDAO;

    private AuthInterface authDAO;

    public StatusService(IFactory factory) {
        super(factory);
        this.factory = factory;

        this.statusDAO = factory.createStatusDAO();
        this.authDAO = factory.createAuthDAO();
    }


//    private TweeterFactory factory = getTweeterFactory();
    public StoryResponse getStoryStatuses(StoryRequest request) {
        if(request.getStatusAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a Status alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }

        boolean tokenCheck = authDAO.checkAuthTokenTime(request.getAuthToken().getToken());

        if (!tokenCheck) {
            throw new RuntimeException("[Bad Request] Token is expired");
        }

        StoryResponse response = statusDAO.getStoryStatues(request);
        System.out.println("status service StoryResponse: " + response);
        return response;
    }

//    StatusDAO getStoryStatusDAO() {
//        return new StatusDAO();
//    }

    public FeedResponse getFeedStatuses(FeedRequest request) {
        if(request.getStatusAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a Status alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }

        boolean tokenCheck = authDAO.checkAuthTokenTime(request.getAuthToken().getToken());

        if (!tokenCheck) {
            throw new RuntimeException("[Bad Request] Token is expired");
        }


        FeedResponse response = statusDAO.getFeedStatues(request);
        return response;
    }

//    StatusDAO getFeedStatusDAO() {
//        return new StatusDAO();
//    }


    public PostStatusResponse postStatus(PostStatusRequest request) {
//        if (request.getStatus().post == null) {
//            throw new RuntimeException("[Bad Request] Request needs to have a Status");
//        }

        System.out.println("status service PostStatusRequest: " + request.getStatus());

        boolean tokenCheck = authDAO.checkAuthTokenTime(request.getAuthtoken().getToken());

        if (!tokenCheck) {
            throw new RuntimeException("[Bad Request] Token is expired");
        }


        PostStatusResponse response = statusDAO.postStatus(request);
        System.out.println("status service PostStatusResponse: " + response);
        return response;

    }

}
