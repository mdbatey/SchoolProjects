package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimplePageObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleValueObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingPresenter extends PagedPresenter<User> implements FollowService.PageUserObserver {

    private static final int PAGE_SIZE = 10;


    @Override
    public void getItems(AuthToken authToken, User targetUser, int pageSize, User lastItem) {
        FollowService service = new FollowService();
        service.LoadMoreFollowing(targetUser, pageSize, lastItem, this );
    }

    @Override
    public void handleSuccess(List<User> items, Boolean hasMorePages) {
        handlePageSuccessful(items, hasMorePages);
    }


    public GetFollowingPresenter(PresenterPageView<User> view) {
        super(view);

    }


    @Override
    public void handleSuccess(Bundle data) {
        handleUserSuccess(data);
    }
}
