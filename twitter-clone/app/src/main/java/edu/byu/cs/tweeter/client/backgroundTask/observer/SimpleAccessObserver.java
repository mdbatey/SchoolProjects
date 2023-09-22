package edu.byu.cs.tweeter.client.backgroundTask.observer;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface SimpleAccessObserver extends ServiceObserver{

    void handleSuccess(User user, AuthToken authToken);
}
