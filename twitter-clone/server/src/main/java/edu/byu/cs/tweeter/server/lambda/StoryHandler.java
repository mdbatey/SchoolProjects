package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.factory.TweeterFactory;
import edu.byu.cs.tweeter.server.service.StatusService;


public class StoryHandler implements RequestHandler<StoryRequest, StoryResponse> {


    @Override
    public StoryResponse handleRequest(StoryRequest request, Context context) {
        StatusService service = new StatusService(new TweeterFactory());
        return service.getStoryStatuses(request);
    }
}
