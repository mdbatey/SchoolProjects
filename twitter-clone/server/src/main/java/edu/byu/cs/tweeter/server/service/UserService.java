package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.UserResponse;
import edu.byu.cs.tweeter.server.dao.AuthInterface;
import edu.byu.cs.tweeter.server.dao.AuthtokenDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.dao.UserInterface;
import edu.byu.cs.tweeter.server.factory.IFactory;
import edu.byu.cs.tweeter.server.factory.TweeterFactory;
import edu.byu.cs.tweeter.util.FakeData;

public class UserService  extends Service{

    private IFactory factory;

    private UserInterface userDAO;
    private AuthInterface authtokenDAO;

    public UserService(IFactory factory) {
        super(factory);
        this.factory = factory;

        this.userDAO = factory.createUserDAO();
        this.authtokenDAO = factory.createAuthDAO();
    }
//    private TweeterFactory factory = getTweeterFactory();
//    private UserDAO userDAO = factory.getUserDAO();
//
//    private AuthtokenDAO authtokenDAO = factory.getAuthtokenDAO();

    public LoginResponse login(LoginRequest request) {
        if(request.getUsername() == null){
            throw new RuntimeException("[Bad Request] Missing a username");
        } else if(request.getPassword() == null) {
            throw new RuntimeException("[Bad Request] Missing a password");
        }

        if (userDAO.loginUser(request) == false) {
            throw new RuntimeException("[Bad Request] Invalid username or password");
        }



//        User user = getDummyUser();
        //        AuthToken authToken = getDummyAuthToken();


        User user = userDAO.getUser(request.getUsername());
        AuthToken authToken = authtokenDAO.createAuthToken(request.getUsername());


        return new LoginResponse(user, authToken);
    }

    public RegisterResponse register(RegisterRequest request) {
        if(request.getUsername() == null){
            throw new RuntimeException("[Bad Request] Missing a username");
        } else if(request.getPassword() == null) {
            throw new RuntimeException("[Bad Request] Missing a password");
        } else if(request.getFirstName() == null) {
            throw new RuntimeException("[Bad Request] Missing a first name");
        } else if(request.getLastName() == null) {
            throw new RuntimeException("[Bad Request] Missing a last name");
        } else if(request.getImageURL() == null) {
            throw new RuntimeException("[Bad Request] Missing an image url");
        }


        //  Generates dummy data. Replace with a real implementation.
//        User user = getDummyUser();

        userDAO.registerNewUser(request);

        AuthToken authToken = authtokenDAO.createAuthToken(request.getUsername());

        User user = new User(request.getFirstName(), request.getLastName(),
                request.getUsername(), "https://2023-340-bucket.s3.us-west-2.amazonaws.com/" + request.getUsername());

        return new RegisterResponse(user, authToken);
    }

    public UserResponse getUser(UserRequest request) {
        if(request.getUsername() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if (request.getAuthToken() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have an auth token");
        }

//        System.out.println("UserRequest: " + request.getUsername());
        //: Generates dummy data. Replace with a real implementation.
//        User user = getFakeData().findUserByAlias(request.getUsername());

        System.out.println("get user authtoken: " + request.getAuthToken());
        boolean tokenCheck = authtokenDAO.checkAuthTokenTime(request.getAuthToken().getToken());

        if(tokenCheck == false) {
            throw new RuntimeException("[Bad Request] Auth token is expired");
        }

        User user = userDAO.getUser(request.getUsername());
        AuthToken authToken = request.getAuthToken();//getDummyAuthToken();

        return new UserResponse(user, authToken);




//        if (request.getUsername() == null) {
//            throw new RuntimeException("[Bad Request] Missing a username ");
//        } else if (request.getAuthtoken() == null) {
//            throw new RuntimeException("[Bad Request] Missing an auth token");
//        }
//
//        // TODO: Generates dummy data. Replace with a real implementation.
//        User user = getDummyUser();
//        AuthToken authToken = getDummyAuthToken();
//
//        return new UserResponse(user, authToken);
    }



    public LogoutResponse logout(LogoutRequest request) {
//        if(request.getUsername() == null) {
//            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
//        } else if (request.getAuthToken() == null) {
//            throw new RuntimeException("[Bad Request] Request needs to have an auth token");
//        }


        authtokenDAO.logoutUser(request.getAuthToken().getToken());

        return new LogoutResponse(request.getAuthToken());

    }


    /**
     * Returns the dummy user to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy user.
     *
     * @return a dummy user.
     */
//    User getDummyUser() {
//        return getFakeData().getFirstUser();
//    }

    /**
     * Returns the dummy auth token to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy auth token.
     *
     * @return a dummy auth token.
     */
//    AuthToken getDummyAuthToken() {
//        return getFakeData().getAuthToken();
//    }

    /**
     * Returns the {@link FakeData} object used to generate dummy users and auth tokens.
     * This is written as a separate method to allow mocking of the {@link FakeData}.
     *
     * @return a {@link FakeData} instance.
     */
//    FakeData getFakeData() {
//        return FakeData.getInstance();
//    }





}
