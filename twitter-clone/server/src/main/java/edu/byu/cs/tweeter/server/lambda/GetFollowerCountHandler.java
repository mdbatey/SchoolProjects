package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.server.factory.TweeterFactory;
import edu.byu.cs.tweeter.server.service.FollowService;

public class GetFollowerCountHandler implements RequestHandler<GetCountRequest, GetCountResponse> {


    @Override
    public GetCountResponse handleRequest(GetCountRequest request, Context context) {
        FollowService service = new FollowService(new TweeterFactory());
        return service.getFollowerCount(request);
    }
}
