package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleAccessObserver;
import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends AcessPresenter implements UserService.AccessObserver{

    public LoginPresenter(PresenterAccessView view) {
        super(view);
    }

    @Override
    public void handleSuccess(User user, AuthToken authToken) {
        view.accessSuccessful(user, authToken);
    }

    public void initiateLogin(String alias, String password) {
//        String validationMessage = validateLogin(alias, password);
        String validationMessage = validate(null, null, alias, password, null);

        if (validationMessage == null) {
            view.displayMessage("Logging in...");
            UserService service = new UserService();
            service.login(alias, password, this);

        } else {
            view.displayErrorMessage(validationMessage);
        }

    }

//    public String validateLogin(String alias, String password) {
//        if (alias.length() > 0 && alias.charAt(0) != '@') {
//            return "Alias must begin with @.";
//        }
//        if (alias.length() < 2) {
//            return "Alias must contain 1 or more characters after the @.";
//        }
//        if (password.length() == 0) {
//            return "Password cannot be empty.";
//        }
//
//        return null;
//    }



}
