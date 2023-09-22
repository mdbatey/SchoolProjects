package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface PresenterAccessView extends PresenterView{

    void accessSuccessful(User user, AuthToken authtoken);
}
