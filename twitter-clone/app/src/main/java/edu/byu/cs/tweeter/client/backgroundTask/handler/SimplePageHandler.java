package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimplePageObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class SimplePageHandler<T> extends BackgroundTaskHandler<SimplePageObserver>{

    public SimplePageHandler(SimplePageObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, SimplePageObserver observer) {

        List<T> items = (List<T>) data.getSerializable(GetFollowingTask.ITEMS_KEY);
        Boolean hasMorePages = data.getBoolean(GetFollowingTask.MORE_PAGES_KEY);

        observer.handleSuccess(items, hasMorePages);
    }
}
