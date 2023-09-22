package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleValueObserver;

public class SimpleValueHandler extends BackgroundTaskHandler<SimpleValueObserver>{

    public SimpleValueHandler(SimpleValueObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, SimpleValueObserver observer) {

        observer.handleSuccess(data);
    }
}
