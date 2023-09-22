package edu.byu.cs.tweeter.client.model.service;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimpleValueHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleValueObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainService extends Service{


    public interface NotificationObserver extends SimpleNotificationObserver{
        @Override
        void handleSuccess();

    }

    public interface ValueObserver extends SimpleValueObserver{

        @Override
        void handleSuccess(Bundle data);

    }

    public void ServiceIsFollower(User selectedUser, SimpleValueObserver observer){
        IsFollowerTask getTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new SimpleValueHandler(observer));
        executeTask(getTask);
    }

//    public void ServiceFollowingAndFollowers(User selectedUser, SimpleValueObserver followersObserver, SimpleValueObserver followingObserver){
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//
//        // Get count of most recently selected user's followers.
//        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
//                selectedUser, new SimpleValueHandler(followersObserver));
//        executor.execute(followersCountTask);
//
//        // Get count of most recently selected user's followees (who they are following)
//        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
//                selectedUser, new SimpleValueHandler(followingObserver));
//        executor.execute(followingCountTask);
//    }

    public void ServiceFollowingCount(User selectedUser, SimpleValueObserver observer){
        GetFollowingCountTask getTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new SimpleValueHandler(observer));
        executeTask(getTask);
    }

    public void ServiceFollowersCount(User selectedUser, SimpleValueObserver observer){
        GetFollowersCountTask getTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new SimpleValueHandler(observer));
        executeTask(getTask);
    }

    public void ServiceStatus (Status newStatus,  SimpleNotificationObserver observer){ // PostStatusObserver
        PostStatusTask getTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(),
                newStatus, new SimpleNotificationHandler(observer)); //MainHandler.PostStatusHandler(observer)
        executeTask(getTask);
    }

    public void ServiceLogout(SimpleNotificationObserver observer){ //LogoutObserver
        LogoutTask getTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new SimpleNotificationHandler(observer)); //MainHandler.LogoutHandler(observer)
        executeTask(getTask);
    }

    public void ServiceUnfollow(User selectedUser, SimpleNotificationObserver observer){ //UnfollowObserver
        UnfollowTask getTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(),
                selectedUser, new SimpleNotificationHandler(observer));//MainHandler.UnfollowHandler(observer));
        executeTask(getTask);
    }

    public void ServiceFollow(User selectedUser, SimpleNotificationObserver observer){ // FollowObserver // ServiceObserver
        FollowTask getTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(), Cache.getInstance().getCurrUser(),
                selectedUser, new SimpleNotificationHandler(observer)); //MainHandler.FollowHandler(observer));
        executeTask(getTask);
    }






}
