package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.observer.SimplePageObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersPresenter extends PagedPresenter<User> implements FollowService.PageUserObserver {

    private static final int PAGE_SIZE = 10;


    @Override
    public void getItems(AuthToken authToken, User targetUser, int pageSize, User lastItem) {
        FollowService service = new FollowService();
        service.LoadMoreFollowers(targetUser, pageSize, lastItem, this );
    }

    @Override
    public void handleSuccess(List<User> items, Boolean hasMorePages) {
        handlePageSuccessful(items, hasMorePages);
    }


    public GetFollowersPresenter(PresenterPageView<User> view) {
        super(view);
    }

    @Override
    public void handleSuccess(Bundle data) {
        handleUserSuccess(data);
    }


//    /**
//     * Causes the Adapter to display a loading footer and make a request to get more following
//     * data.
//     */
//    public void loadMoreItems(User user) {
//        setTargetUser(user);
//        setAuthToken(Cache.getInstance().getCurrUserAuthToken());
//        loadMoreItems();
//    }
//
//    private class GetFollowersObserver implements SimplePageObserver<User> {
//        @Override
//        public void handleFailure(String message){
//                setLoading(false);
//                view.setLoadingFooter(false);
//                view.displayMessage(message);
//            }
//
//        @Override
//        public void handleException(Exception ex){
//                setLoading(false);
//                view.setLoadingFooter(false);
//                view.displayMessage("Failed to get followers because of exception: " + ex.getMessage());
//            }
//
//        @Override // MB fix later in presenters de-dupe
//        public <T> void handleSuccess(List<T> followers, Boolean hasMorePages) {
//            setLoading(false);
//            view.setLoadingFooter(false);
//
//            User lastFollower = (followers.size() > 0) ? (User) followers.get(followers.size() - 1) : null;
//            setLastItem(lastFollower);
//
//            setHasMorePages(hasMorePages);
//            view.addMoreItems((List<User>) followers);
//            //           followersRecyclerViewAdapter.addItems(followers);
//
//        }
//
//    }


}
