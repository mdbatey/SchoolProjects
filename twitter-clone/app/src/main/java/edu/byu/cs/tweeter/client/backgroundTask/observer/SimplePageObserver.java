package edu.byu.cs.tweeter.client.backgroundTask.observer;

import java.util.List;

public interface SimplePageObserver<T> extends ServiceObserver{

//    void handleSuccess();

    public void handleSuccess(List<T> items, Boolean hasMorePages);
}
