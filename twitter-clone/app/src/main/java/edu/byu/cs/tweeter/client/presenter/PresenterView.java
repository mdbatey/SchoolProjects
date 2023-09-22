package edu.byu.cs.tweeter.client.presenter;

public interface PresenterView {


    abstract void displayErrorMessage(String message);
    abstract void displayMessage(String message);
}
