package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimpleAccessHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimplePageHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimpleValueHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleAccessObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimplePageObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleValueObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class Service {

    public <T> void executeTask(T task) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute((Runnable) task);
    }


    ////////////////////////////////////////////
//
//    // USER SERVICE
//    public void login(String alias, String password, SimpleAccessObserver observer) {
//        LoginTask getTask = new LoginTask(alias, password, new SimpleAccessHandler(observer));
//        executeTask(getTask);
//    }
//
//    public void register(String alias, String password, String firstName, String lastName, String imageUrl, SimpleAccessObserver observer) {
//        RegisterTask getTask = new RegisterTask(alias, password, firstName, lastName, imageUrl, new SimpleAccessHandler(observer));
//        executeTask(getTask);
//
//    }
//
//    public void getUser(String userAlias, SimpleValueObserver observer) {
//        GetUserTask getTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
//                userAlias, new SimpleValueHandler(observer));
//        executeTask(getTask);
//
//    }
//
//
//    //FOLLOW SERVICE
//    public void LoadMoreFollowing(User user, int pageSize, User lastFollowee, SimplePageObserver observer) {
//        GetFollowingTask getTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(),
//                user, pageSize, lastFollowee, new SimplePageHandler<User>(observer));
//        executeTask(getTask);
//
//    }
//
//    public void LoadMoreFollowers(User user, int pageSize, User lastFollower, SimplePageObserver observer) {
//        GetFollowersTask getTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
//                user, pageSize, lastFollower, new SimplePageHandler<User>(observer));
//        executeTask(getTask);
//    }
//
//    //STATUS SERVICE
//    public void LoadFeed(User user, int pageSize, Status lastStatus, SimplePageObserver observer) {
//
//        GetFeedTask getTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
//                user, pageSize, lastStatus, new SimplePageHandler<Status>(observer));
//        executeTask(getTask);
//    }
//
//    public void LoadStory(User user, int pageSize, Status lastStatus, SimplePageObserver observer) {
//
//        GetStoryTask getTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
//                user, pageSize, lastStatus, new SimplePageHandler<Status>(observer));
//        executeTask(getTask);
//    }
//
//
//    // MAIN SERVICE
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
//
//    public void ServiceStatus (Status newStatus,  SimpleNotificationObserver observer){ // PostStatusObserver
//        PostStatusTask getTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
//                newStatus, new SimpleNotificationHandler(observer)); //MainHandler.PostStatusHandler(observer)
//        executeTask(getTask);
//    }
//
//    public void ServiceLogout(SimpleNotificationObserver observer){ //LogoutObserver
//        LogoutTask getTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new SimpleNotificationHandler(observer)); //MainHandler.LogoutHandler(observer)
//        executeTask(getTask);
//    }
//
//    public void ServiceUnfollow(User selectedUser, SimpleNotificationObserver observer){ //UnfollowObserver
//        UnfollowTask getTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
//                selectedUser, new SimpleNotificationHandler(observer));//MainHandler.UnfollowHandler(observer));
//        executeTask(getTask);
//    }
//
//    public void ServiceFollow(User selectedUser, SimpleNotificationObserver observer){ // FollowObserver // ServiceObserver
//        FollowTask getTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
//                selectedUser, new SimpleNotificationHandler(observer)); //MainHandler.FollowHandler(observer));
//        executeTask(getTask);
//    }
//
//    public void ServiceIsFollower(User selectedUser, SimpleValueObserver observer){
//        IsFollowerTask getTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
//                Cache.getInstance().getCurrUser(), selectedUser, new SimpleValueHandler(observer));
//        executeTask(getTask);
//    }

}
