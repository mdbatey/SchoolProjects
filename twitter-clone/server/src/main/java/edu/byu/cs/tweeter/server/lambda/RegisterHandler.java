package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.factory.TweeterFactory;
import edu.byu.cs.tweeter.server.service.UserService;

/**
 * An AWS lambda function that registers a user in and returns the user object and an auth code for
 * a successful login.
 */
public class RegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse> {
        @Override
        public RegisterResponse handleRequest(RegisterRequest registerRequest, Context context) {
            UserService userService = new UserService(new TweeterFactory());
            return userService.register(registerRequest);
        }
    }