package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.AuthenticateTask;
import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleAccessObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class SimpleAccessHandler extends BackgroundTaskHandler<SimpleAccessObserver> {

    public SimpleAccessHandler(SimpleAccessObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, SimpleAccessObserver observer) {
        User accessUser = (User) data.getSerializable(AuthenticateTask.USER_KEY);
        AuthToken authToken = (AuthToken) data.getSerializable(AuthenticateTask.AUTH_TOKEN_KEY);

        // Cache user session information
        Cache.getInstance().setCurrUser(accessUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);

        observer.handleSuccess(accessUser, authToken);
    }


}
