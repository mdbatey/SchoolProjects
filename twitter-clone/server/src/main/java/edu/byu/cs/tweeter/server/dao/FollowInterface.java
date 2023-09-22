package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;

public interface FollowInterface {
    FollowingResponse getFollowees(FollowingRequest request);

    FollowersResponse getFollowers(FollowersRequest request);

    User follow(FollowRequest request);

    User unFollow(UnFollowRequest request);

    boolean isFollower(IsFollowerRequest request);
}
