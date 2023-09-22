package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimplePageObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleValueObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.Service;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status> implements StatusService.PageStatusObserver{

    private static final int PAGE_SIZE = 10;

    @Override
    public void handleSuccess(List<Status> items, Boolean hasMorePages) {
        handlePageSuccessful(items, hasMorePages);
    }

    public StoryPresenter(PresenterPageView<Status> view) {
        super(view);

    }

    @Override
    public void getItems(AuthToken authToken, User targetUser, int pageSize, Status lastItem) {
        StatusService service = new StatusService();
        service.LoadStory(targetUser, pageSize, lastItem, this );
    }


    @Override
    public void handleSuccess(Bundle data) {
        handleUserSuccess(data);
    }
}
