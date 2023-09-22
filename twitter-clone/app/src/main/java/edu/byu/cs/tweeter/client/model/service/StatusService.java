package edu.byu.cs.tweeter.client.model.service;

    //MB  for feed and story

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimplePageHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimplePageObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends Service{


    public interface PageStatusObserver extends SimplePageObserver<Status> {

        @Override
        void handleSuccess(List<Status> items, Boolean hasMorePages);
    }


    public void LoadFeed(User user, int pageSize, Status lastStatus, SimplePageObserver observer) {

        GetFeedTask getTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new SimplePageHandler<Status>(observer));
        executeTask(getTask);
    }

    public void LoadStory(User user, int pageSize, Status lastStatus, SimplePageObserver observer) {

        GetStoryTask getTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new SimplePageHandler<Status>(observer));
        executeTask(getTask);
    }

}
