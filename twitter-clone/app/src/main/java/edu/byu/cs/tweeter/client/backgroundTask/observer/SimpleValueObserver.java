package edu.byu.cs.tweeter.client.backgroundTask.observer;

import android.os.Bundle;

public interface SimpleValueObserver<T> extends ServiceObserver{

    void handleSuccess(Bundle data);
}
