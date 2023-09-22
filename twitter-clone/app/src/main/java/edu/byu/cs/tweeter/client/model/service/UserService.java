package edu.byu.cs.tweeter.client.model.service;

import android.os.Bundle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimpleAccessHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimpleValueHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleAccessObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimplePageObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleValueObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService extends Service{


//    public interface UserObserver extends SimpleValueObserver {
////        void handleSuccess(User user);
////        void handleFailure(String message);
////        void handleException(Exception ex);
//    }


//    public <T> void executeTask(T task) {
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute((Runnable) task);
//    }
//
//

    public interface AccessObserver extends SimpleAccessObserver {

        @Override
        void handleSuccess(User user, AuthToken authToken);

    }

    public interface UserObserver extends SimpleValueObserver {
        @Override
        void handleSuccess(Bundle data);
    }



    public void login(String alias, String password, SimpleAccessObserver observer) {
        LoginTask getTask = new LoginTask(alias, password, new SimpleAccessHandler(observer));
        executeTask(getTask);
    }

    public void register(String alias, String password, String firstName, String lastName, String imageUrl, SimpleAccessObserver observer) {
        RegisterTask getTask = new RegisterTask(alias, password, firstName, lastName, imageUrl, new SimpleAccessHandler(observer));
        executeTask(getTask);

    }

    public void getUser(String userAlias, SimpleValueObserver observer) {
        GetUserTask getTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new SimpleValueHandler(observer));
        executeTask(getTask);

    }
}

