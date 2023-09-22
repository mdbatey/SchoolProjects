package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleValueObserver;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class UserPresenter extends Presenter<PresenterPageView<User>>  implements UserService.UserObserver {
    public UserPresenter(PresenterPageView<User> view) {
        super(view);
    } //Presenter<PresenterPageView<Status>>


    // do not need this because in the other presenter that are needed

//    View view;
//
//    public UserPresenter(View view) {
//        this.view = view;
//    }



    public void getUserData(String alias) {

        view.displayMessage("Getting user's profile...");
        UserService service = new UserService();
        service.getUser(alias, this);

    }

    @Override
    public void handleSuccess(Bundle data) {
        User user = (User) data.getSerializable(GetUserTask.USER_KEY);
        view.userSuccessful(user);
    }


//
//    public interface View {
//        public void displayInfoMessage(String message);
//        public void displayErrorMessage(String message);
//        public void userSuccessful(User user);
//    }


//    private class GetUserObserver implements SimpleValueObserver {
//
////    @Override
////    public void handleSuccess(User user) {
////        view.userSuccessful(user);
////    }
//
//        @Override
//        public void handleSuccess(Bundle data) {
//            User user = (User) data.getSerializable(GetUserTask.USER_KEY);
//            view.userSuccessful(user);
//
//        }
//
//        @Override
//        public void handleFailure(String message) {
//            view.displayErrorMessage(message);
//        }
//
//        @Override
//        public void handleException(Exception ex) {
//            view.displayMessage("Failed to get user's profile because of exception: "+ex.getMessage());
//        }
//
//    }
}
