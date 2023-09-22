package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimplePageHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimplePageObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;


//MB for followers and following
public class FollowService extends Service{



    public interface PageUserObserver extends SimplePageObserver<User> {

        @Override
        void handleSuccess(List<User> items, Boolean hasMorePages);
    }


    public void LoadMoreFollowing(User user, int pageSize, User lastFollowee, SimplePageObserver observer) {
        GetFollowingTask getTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollowee, new SimplePageHandler<User>(observer));
        executeTask(getTask);
//        System.out.println("Service Following lastfollower: " + lastFollowee);

    }

    public void LoadMoreFollowers(User user, int pageSize, User lastFollower, SimplePageObserver observer) {
        GetFollowersTask getTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollower, new SimplePageHandler<User>(observer));
        executeTask(getTask);

//        System.out.println("Services LoadMoreFollowers lastfollower: " + lastFollower);
    }




}


