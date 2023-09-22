package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends PagedPresenter<Status> implements StatusService.PageStatusObserver {

    private static final int PAGE_SIZE = 10;

    @Override
    public void handleSuccess(List<Status> items, Boolean hasMorePages) {
        handlePageSuccessful(items, hasMorePages);
    }

    public FeedPresenter(PresenterPageView<Status> view) {
      super(view);

    }

    @Override
    public void getItems(AuthToken authToken, User targetUser, int pageSize, Status lastItem) {
        StatusService service = new StatusService();
        service.LoadFeed(targetUser, pageSize, lastItem,  this ); //new GetFeedObserver()
    }

    @Override
    public void handleSuccess(Bundle data) {
        handleUserSuccess(data);
    }


//    public void loadMoreItems(User user){
//
//        setTargetUser(user);
//        setAuthToken(Cache.getInstance().getCurrUserAuthToken());
//        loadMoreItems();
//    }

//    private class GetFeedObserver implements SimplePageObserver<Status> { // StatusService.FeedObserver
//
//
//        @Override
//        public void handleFailure(String message) {
////            isLoading = false;
//            setLoading(false);
//
//            view.setLoadingFooter(false);
//            view.displayMessage(message);
//        }
//
//        @Override
//        public void handleException(Exception ex) {
////            isLoading = false;
//            setLoading(false);
//
//            view.setLoadingFooter(false);
//            view.displayMessage("Failed to get feed because of exception: " + ex.getMessage());
//
//        }
//
//        @Override // MB fix later in presenters de-dupe
//        public <T> void handleSuccess(List<T> feedStatuses, Boolean hasMorePages) {
////            isLoading = false;
//            setLoading(false);
//            view.setLoadingFooter(false);
//
//            Status lastStatus = (feedStatuses.size() > 0) ? (Status) feedStatuses.get(feedStatuses.size() - 1) : null;
//            setLastItem(lastStatus);
//
//            setHasMorePages(hasMorePages);
//            view.addMoreItems((List<Status>) feedStatuses);
//        }
//
//
////        @Override
////        public void displayError(String message) {
////            isLoading = false;
////            view.setLoadingFooter(false);
////            view.displayMessage(message);
////        }
////
////        @Override
////        public void displayException(Exception ex) {
////            isLoading = false;
////            view.setLoadingFooter(false);
////            view.displayMessage("Failed to get feed because of exception: " + ex.getMessage());
////        }
////
////        @Override
////        public void addFeedStatuses(List<Status> feedStatuses, Boolean hasMorePages) {
////            isLoading = false;
////            view.setLoadingFooter(false);
////            lastStatus = (feedStatuses.size() > 0) ? feedStatuses.get(feedStatuses.size() - 1) : null;
////            setHasMorePages(hasMorePages);
////            view.addMoreItems(feedStatuses);
////
////        }
//    }


//    public void loadUser(String alias){
//
//        view.displayMessage("Getting user's profile...");
//        Service service = new Service();
//        service.getUser(alias, new GetUserObserver());
//
//    }

    //UserPresenter

//    private class GetUserObserver implements SimpleValueObserver {
//
//
//        //        @Override
////        public void handleSuccess(User user) {
////            view.userSuccessful(user);
////        }
////
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
//            view.displayErrorMessage("Failed to get user's profile because of exception: "+ex.getMessage());
//        }
//    }

}
