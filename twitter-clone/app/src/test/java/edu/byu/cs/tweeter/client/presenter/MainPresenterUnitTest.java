package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.MainService;


/*
https://docs.google.com/presentation/d/1AQpDMkeGbSNvo2i8jTyUcKwrb8vcaPBXRLajhHkqtyc/edit#slide=id.p9
 */

public class MainPresenterUnitTest{

    private PresenterMainView mockView;

    private Cache mockCache;
    private MainService mockMainService;

    private MainPresenter.LogoutPresenter logoutPresenterSpy;




    @BeforeEach
    public void setup() {
        // create mocks
        mockView = Mockito.mock(PresenterMainView.class);
        mockMainService = Mockito.mock(MainService.class);
        mockCache = Mockito.mock(Cache.class);


        logoutPresenterSpy = Mockito.spy(new MainPresenter.LogoutPresenter(mockView));


        //assumes you return something from the method
//        Mockito.doReturn(mockMainService).when(mainPresenterSpy).getMainService();

        // checks that the return type in the when is the same as the return type of the method
//        Mockito.when(mainPresenterSpy.getMainService()).thenReturn(mockMainService);
        Mockito.when(logoutPresenterSpy.getMainService()).thenReturn(mockMainService);
        //todo mock the right services to the right presenter


        Cache.setInstance(mockCache);

    }


    /*
    pbulic void logout(){
        view.displayMessage("Logging Out...");

        Service.logout(new Service.LogoutObserver(){
            @Override
            public void logoutSuccessful() {
                Cache.getInstance().clearCache();
                view.clearInfoMessage();
                view.logoutUser();
            }

            @Override
            public void logoutUnsuccessful(String message) {
                String displayMessage = "Failed to logout: " + message;
                view.clearInfoMessage();
                view.displayMessage(message);
            }

            @Override
            public void handleException(Exception ex) {
                view.displayMessage("Failed to logout because of exception: " + ex.getMessage());
                view.clearInfoMessage();
                view.displayMessage(ex.getMessage());
            }
        });

     */


    @Test
    public void testLogout_logoutSuccessful(){

        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.NotificationObserver observer = invocation.getArgument(0, MainService.NotificationObserver.class);
                observer.handleSuccess();
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockMainService).ServiceLogout(Mockito.any());



        logoutPresenterSpy.logout();



        Mockito.verify (mockView).displayMessage("Logging Out...");

        Mockito.verify (mockView).LogoutSuccessful();


    }



    @Test
    public void testLogout_logoutFailedWithMessage(){
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.NotificationObserver observer = invocation.getArgument(0, MainService.NotificationObserver.class);
                observer.handleFailure("Failed to logout: the error message");
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockMainService).ServiceLogout(Mockito.any());



        logoutPresenterSpy.logout();



        Mockito.verify (mockView).displayMessage("Logging Out...");
        Mockito.verify (mockView).displayErrorMessage("Failed to logout: the error message");




    }



    @Test
    public void testLogout_logoutFailedWithException(){
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.NotificationObserver observer = invocation.getArgument(0, MainService.NotificationObserver.class);
                observer.handleException(new Exception("the exception message"));
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockMainService).ServiceLogout(Mockito.any());

        logoutPresenterSpy.logout();


        Mockito.verify (mockView).displayMessage("Logging Out...");
        Mockito.verify (mockView).displayErrorMessage("Failed to logout because of exception: the exception message");

    }

//    private void verifyErrorResult(String s) {
////        Mockito.verify (mockView, Mockito.times(0)).clearCache();
////        Mockito.verify (mockView).clearInfoMessage();
//        Mockito.verify (mockView).displayErrorMessage(s);
//    }


}



