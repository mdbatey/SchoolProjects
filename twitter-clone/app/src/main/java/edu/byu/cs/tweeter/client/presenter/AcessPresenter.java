package edu.byu.cs.tweeter.client.presenter;

import android.widget.ImageView;

public abstract class AcessPresenter extends Presenter<PresenterAccessView> {

    public AcessPresenter(PresenterAccessView view) {
        super(view);
    }


////    public abstract void validateAccess();
//
//    public void initiateAccess(String alias, String password){
//
//    }
//
//    public void initiateAccess(String firstName, String lastName, String alias, String password){
//
//    }

//    public abstract void

    public String validate(String firstName, String lastName, String alias, String password, ImageView imageToUpload) {
        if (firstName == null && lastName == null) {
            if (alias.length() > 0 && alias.charAt(0) != '@') {
                return "Alias must begin with @.";
            }
            if (alias.length() < 2) {
                return "Alias must contain 1 or more characters after the @.";
            }
            if (password.length() == 0) {
                return "Password cannot be empty.";
            }

            return null;
        } else {
            if (firstName.length() == 0) {
                return "First Name cannot be empty.";
            }
            if (lastName.length() == 0) {
                return "Last Name cannot be empty.";
            }
            if (alias.length() == 0) {
                return "Alias cannot be empty.";
            }
            if (alias.charAt(0) != '@') {
                return "Alias must begin with @.";
            }
            if (alias.length() < 2) {
                return "Alias must contain 1 or more characters after the @.";
            }
            if (password.length() == 0) {
                return "Password cannot be empty.";
            }

            if (imageToUpload.getDrawable() == null) {
                return "Profile image must be uploaded.";
            }

            return null;
        }
    }




}
