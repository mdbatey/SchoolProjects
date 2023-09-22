package edu.byu.cs.tweeter.client.presenter;

/*
https://byu.instructure.com/courses/20159/files/6128487?module_item_id=1584408
PagedPresentersClassDiagram.png
 */

import android.view.View;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class Presenter<T extends PresenterView> {

    T view;

    public Presenter(T view) {
        this.view = view;
    }


    public void handleFailure (String message){
        view.displayErrorMessage(message);
    }


    public void handleException (Exception ex){
        view.displayMessage(ex.getMessage());
    }

}
