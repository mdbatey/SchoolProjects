package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface PresenterPageView<T> extends PresenterView {

    //setLoading
    abstract void setLoadingFooter(boolean value);
    abstract void addMoreItems(List<T> items);

    //navigate to user
    abstract void userSuccessful(User user);




}
