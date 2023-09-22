package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimplePageObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleValueObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends Presenter<PresenterPageView<T>> implements UserService.UserObserver{


    private static final int PAGE_SIZE = 10;
//    int pageSize;


    User targetUser;
    AuthToken authToken;
    private T lastItem;
    private boolean hasMorePages;
    private boolean isLoading = false;
//    boolean isGettingUser;


    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
//
    public T getLastItem() {
        return lastItem;
    }

    public void setLastItem(T lastItem) {
        this.lastItem = lastItem;
    }

    //////////////////////////////////////////////////////////

    public PagedPresenter(PresenterPageView<T> view) {
        super(view);
    }
    public void loadMoreItems(User user){
        setTargetUser(user);
        setAuthToken(Cache.getInstance().getCurrUserAuthToken());

        if (!isLoading()) {
            setLoading(true);
            view.setLoadingFooter(true);
            getItems(authToken, targetUser, PAGE_SIZE, lastItem);
        }
    }

    @Override
    public void handleFailure(String message) {
        setLoading(false);
        view.setLoadingFooter(false);
        view.displayMessage(message);
    }


    public abstract void getItems(AuthToken authToken, User targetUser, int pageSize, T lastItem);



//    public abstract String getDescription();

    //////////////////////////////////////////////////////////

    public void handlePageSuccessful(List<T> items, boolean hasMorePages){
        setLoading(false);
        view.setLoadingFooter(false);

        T last = (items.size() > 0) ?  items.get(items.size() - 1) : null;
        setLastItem(last);

        setHasMorePages(hasMorePages);
        view.addMoreItems(items);
    }

    //////////////////////////////////////////////////////////

    public void handleUserSuccess(Bundle data) {
        User user = (User) data.getSerializable(GetUserTask.USER_KEY);
        view.userSuccessful(user);
    }

    public void getUser(String alias){
        view.displayMessage("Getting user's profile...");
        UserService service = new UserService();
        service.getUser(alias, this);
    }

//    private class GetUserObserver implements UserService.UserObserver {
//
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
//            view.displayErrorMessage("Failed to get user's profile because of exception: "+ex.getMessage());
//        }
//    }
}
