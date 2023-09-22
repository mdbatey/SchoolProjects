package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleAccessObserver;
import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends AcessPresenter implements UserService.AccessObserver{



    public RegisterPresenter(PresenterAccessView view) {
        super(view);
    }

    @Override
    public void handleSuccess(User user, AuthToken authToken) {
        view.accessSuccessful(user, authToken);
    }


//    public interface View extends PresenterAccessView{
////        public void displayInfoMessage(String message);
////        public void displayErrorMessage(String message);
////
////        void registerSuccess(User user, AuthToken authToken);
//    }

    public void initiateRegistration(String firstName, String lastName, String alias, String password, ImageView imageToUpload) {
//        String validationMessage = validateRegistration(firstName, lastName, alias, password, imageToUpload);
        String validationMessage = validate(firstName, lastName, alias, password, imageToUpload);

        if (validationMessage == null) {
            view.displayMessage("Registering...");
            // Convert image to byte array.
            Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] imageBytes = bos.toByteArray();

            // Intentionally, Use the java Base64 encoder so it is compatible with M4.
            String imageBytesBase64 = Base64.getEncoder().encodeToString(imageBytes);

            UserService service = new UserService();
            service.register(firstName, lastName, alias, password, imageBytesBase64, this);
        } else {
            view.displayErrorMessage(validationMessage);
        }
    }


//    public String validateRegistration(String firstName, String lastName, String alias, String password, ImageView imageToUpload) {
//        if (firstName.length() == 0) {
//            return "First Name cannot be empty.";
//        }
//        if (lastName.length() == 0) {
//            return "Last Name cannot be empty.";
//        }
//        if (alias.length() == 0) {
//            return "Alias cannot be empty.";
//        }
//        if (alias.charAt(0) != '@') {
//            return "Alias must begin with @.";
//        }
//        if (alias.length() < 2) {
//            return "Alias must contain 1 or more characters after the @.";
//        }
//        if (password.length() == 0) {
//            return "Password cannot be empty.";
//        }
//
//        if (imageToUpload.getDrawable() == null) {
//            return "Profile image must be uploaded.";
//        }
//
//        return null;
//    }



//    private class RegisterObserver implements SimpleAccessObserver {
//
//
//        @Override
//        public void handleSuccess(User user, AuthToken authToken) {
//            view.accessSuccessful(user, authToken);
//        }
//
//        @Override
//        public void handleFailure(String message) {
//            view.displayMessage(message);
//        }
//
//        @Override
//        public void handleException(Exception ex) {
//            view.displayMessage(ex.getMessage());
//        }
//
//    }



}
