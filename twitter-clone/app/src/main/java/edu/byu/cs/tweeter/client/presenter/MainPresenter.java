package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.MainService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;


public class MainPresenter extends Presenter<PresenterMainView> { //implements MainService.NotificationObserver, MainService.ValueObserver  {


    private static MainService mainService;

    public MainPresenter(PresenterMainView view) {
        super(view);
    }

    public MainService getMainService(){

        if (mainService == null) {
            mainService = new MainService();
        }
        return mainService;
    }


    public static class GetFollowersCountPresenter extends Presenter<PresenterMainView> implements MainService.ValueObserver{

        public GetFollowersCountPresenter(PresenterMainView view) {
            super(view);
        }

        @Override
        public void handleSuccess(Bundle data) {
            int count = data.getInt(GetFollowersCountTask.COUNT_KEY);
            view.GetFollowersCountSuccessful(count);
        }

        @Override
        public void handleException (Exception ex){
            view.displayMessage("Failed to get followers count because of exception: " + ex.getMessage());
        }

        public void getFollowersCount(User selectedUser) {
            MainService service = new MainService();
            service.ServiceFollowersCount(selectedUser, this);
        }
    }


//    private class GetFollowersCountObserver implements SimpleValueObserver {
////        @Override
////        public void handleSuccess (int count) {
////            view.GetFollowersCountSuccessful(count);
////        }
//        @Override
//        public void handleSuccess(Bundle data) {
//            int count = data.getInt(GetFollowersCountTask.COUNT_KEY);
//            view.GetFollowersCountSuccessful(count);
//        }
//
//        @Override
//        public void handleFailure (String message){
//            view.displayMessage(message);
//        }
//
//        @Override
//        public void handleException (Exception ex){
//            view.displayMessage("Failed to get followers count because of exception: " + ex.getMessage());
//        }
//    }

    public static class GetFollowingCountPresenter extends Presenter<PresenterMainView> implements MainService.ValueObserver{

        public GetFollowingCountPresenter(PresenterMainView view) {
            super(view);
        }

        @Override
        public void handleSuccess(Bundle data) {
            int count = data.getInt(GetFollowingCountTask.COUNT_KEY);
            view.GetFollowingCountSuccessful(count);
        }

        @Override
        public void handleException (Exception ex){
            view.displayMessage("Failed to get following count because of exception: " + ex.getMessage());
        }

        public void getFollowingCount(User selectedUser) {
            MainService service = new MainService();
            service.ServiceFollowingCount(selectedUser, this);
        }
    }

//    private class GetFollowingCountObserver implements  SimpleValueObserver{
//
//        @Override
//        public void handleSuccess(Bundle data) {
//            int count = data.getInt(GetFollowingCountTask.COUNT_KEY);
//            view.GetFollowingCountSuccessful(count);
//        }
//
//        @Override
//        public void handleFailure (String message){
//            view.displayMessage(message);
//        }
//
//        @Override
//        public void handleException (Exception ex){
//            view.displayMessage("Failed to get following count because of exception: " + ex.getMessage());
//        }
//
//
//    }
//
//    public void FollowingAndFollowers(User selectedUser) {
//
//        MainService service = new MainService();
//        service.ServiceFollowingCount(selectedUser, new GetFollowingCountObserver());
//        service.ServiceFollowersCount(selectedUser, new GetFollowersCountObserver());
////        service.ServiceFollowingAndFollowers(selectedUser, new GetFollowersCountObserver(), new GetFollowingCountObserver());
//    }


    public static class IsFollowerPresenter extends Presenter<PresenterMainView> implements MainService.ValueObserver{

        public IsFollowerPresenter(PresenterMainView view) {
            super(view);
        }

        @Override
        public void handleSuccess(Bundle data) {
            boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
            view.IsFollowerSuccessful(isFollower);
        }

        @Override
        public void handleException (Exception ex){
            view.displayMessage("Failed to determine following relationship because of exception: " + ex.getMessage());
        }

        public void IsFollower(User selectedUser){

            MainService service = new MainService();
            service.ServiceIsFollower(selectedUser, this);

        }
    }
//    private class IsFollowerObserver implements SimpleValueObserver {
//
//
//        @Override
//        public void handleSuccess(Bundle data) {
//            boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
//            view.IsFollowerSuccessful(isFollower);
//        }
//
//        @Override
//        public void handleFailure (String message){
//            view.displayMessage(message);
//        }
//
//        @Override
//        public void handleException (Exception ex){
//            view.displayMessage("Failed to determine following relationship because of exception: " + ex.getMessage());
//        }
//
//
//    }





//    private class LogoutObserver implements SimpleNotificationObserver { // SimpleNotificationObserver
//
//        @Override
//        public void handleFailure(String message) {
//            view.displayMessage(message);
//
//        }
//
//        @Override
//        public void handleException(Exception ex) {
//            view.displayMessage("Failed to logout because of exception: " + ex.getMessage());
//        }
//
//        @Override
//        public void handleSuccess() {
//            view.LogoutSuccessful();
//        }
//    }





    public static class LogoutPresenter extends Presenter<PresenterMainView> implements MainService.NotificationObserver {

        public LogoutPresenter(PresenterMainView view) {
            super(view);
        }

        @Override
        public void handleSuccess() {
            view.LogoutSuccessful();
        }

        @Override
        public void handleException(Exception ex) {
            view.displayErrorMessage("Failed to logout because of exception: " + ex.getMessage());
        }

        public void logout(){
            view.displayMessage("Logging Out...");

//            MainService service = new MainService();
            MainService service = getMainService();
           service.ServiceLogout(this);
        }

        public MainService getMainService(){

            if (mainService == null) {
                mainService = new MainService();
            }
            return mainService;
        }
    }

    public static class FollowPresenter extends Presenter<PresenterMainView> implements MainService.NotificationObserver {
        public FollowPresenter(PresenterMainView view) {
            super(view);
        }

        @Override
        public void handleSuccess() {
            view.FollowSuccessful();
            view.setFollow(true);
        }


        @Override
        public void handleException(Exception ex) {
            view.displayMessage("Failed to follow because of exception: " + ex.getMessage());
        }

        public void Follow(User selectedUser){
            view.displayMessage("Adding " + selectedUser.getName() + "...");
            MainService service = new MainService();
            service.ServiceFollow(selectedUser, this);
        }

    }

//    private class FollowObserver implements SimpleNotificationObserver { //ServiceObserver {//MainService.FollowObserver // SimpleNotificationObserver
//
//        @Override
//        public void handleSuccess () {
//            view.FollowSuccessful();
//            view.setFollow(true);
//        }
//
//        @Override
//        public void handleFailure (String message){
//            view.displayMessage(message);
//            view.setFollow(true);
//
//        }
//
//        @Override
//        public void handleException (Exception ex){
//            view.displayMessage("Failed to follow because of exception: " + ex.getMessage());
//            view.setFollow(true);
//        }
//    }

//    private MainService.FollowObserver FollowObserver = new FollowObserver();


    public static class UnfollowPresenter extends Presenter<PresenterMainView> implements MainService.NotificationObserver {
        public UnfollowPresenter(PresenterMainView view) {
            super(view);
        }

        @Override
        public void handleSuccess () {
            view.UnfollowSuccessful();
            view.setFollow(true);
        }


        @Override
        public void handleException (Exception ex){
            view.displayMessage("Failed to unfollow because of exception: " + ex.getMessage());
        }

        public void Unfollow(User selectedUser){

            view.displayMessage("Removing " + selectedUser.getName() + "...");
            MainService service = new MainService();
            service.ServiceUnfollow(selectedUser, this);
        }


    }

//
//    private class UnfollowObserver implements SimpleNotificationObserver { //SimpleNotificationObserver
//
//        @Override
//        public void handleSuccess () {
//            view.UnfollowSuccessful();
//            view.setFollow(true);
//        }
//
//        @Override
//        public void handleFailure (String message){
//            view.displayMessage(message);
//            view.setFollow(true);
//        }
//
//        @Override
//        public void handleException (Exception ex){
//            view.displayMessage("Failed to unfollow because of exception: " + ex.getMessage());
//            view.setFollow(true);
//        }
//    }


    public static class PostStatusPresenter extends Presenter<PresenterMainView> implements MainService.NotificationObserver {
        public PostStatusPresenter(PresenterMainView view) {
            super(view);
        }

        @Override
        public void handleSuccess () {
            view.PostStatusSuccessful();
            view.displayMessage("Successfully Posted!");
        }

        @Override
        public void handleException (Exception ex){
            view.displayErrorMessage("Failed to post status because of exception: " + ex.getMessage());
        }
        @Override
        public void handleFailure (String message){
            view.displayErrorMessage("Failed to post status: " + message);
        }

        public void PostStatus(Status newStatus){
//            MainService service = new MainService();
            MainService service = getMainService();
            service.ServiceStatus(newStatus, this);
        }


        public MainService getMainService(){

            if (mainService == null) {
                mainService = new MainService();
            }
            return mainService;
        }
    }

//    private class PostStatusObserver implements SimpleNotificationObserver { //SimpleNotificationObserver
//
//        @Override
//        public void handleSuccess () {
//            view.PostStatusSuccessful();
//
//            view.displayMessage("Successfully Posted!");
//
//        }
//
//        @Override
//        public void handleFailure (String message){
//            view.displayMessage(message);
//        }
//
//        @Override
//        public void handleException (Exception ex){
//            view.displayMessage("Failed to post status because of exception: " + ex.getMessage());
//        }
//    }














}
