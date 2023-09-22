package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.model.net.response.UnFollowResponse;
import edu.byu.cs.tweeter.server.factory.TweeterFactory;
import edu.byu.cs.tweeter.server.service.FollowService;

public class UnFollowHandler implements RequestHandler<UnFollowRequest, UnFollowResponse> {

        @Override
        public UnFollowResponse handleRequest(UnFollowRequest unFollowRequest, Context context) {
            FollowService service = new FollowService(new TweeterFactory());
            return service.unFollow(unFollowRequest);
        }
}
