package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.model.service.MainService;
import edu.byu.cs.tweeter.model.domain.Status;

public class MainPresenterUnitTestPostTask {


    private PresenterMainView mockView;

    private MainService mockMainService;

    private MainPresenter.PostStatusPresenter postStatusPresenterSpy;


    @BeforeEach
    public void setup() {
        // create mocks
        mockView = Mockito.mock(PresenterMainView.class);
        mockMainService = Mockito.mock(MainService.class);

        postStatusPresenterSpy = Mockito.spy(new MainPresenter.PostStatusPresenter(mockView));

        Mockito.when(postStatusPresenterSpy.getMainService()).thenReturn(mockMainService);

    }


    @Test
    public void testPostStatus_postSuccessful(){
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.NotificationObserver observer = invocation.getArgument(1,MainService.NotificationObserver.class);//index 1 is the second argument hence the observer
                observer.handleSuccess();
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockMainService).ServiceStatus(Mockito.any(),Mockito.any(MainService.NotificationObserver.class));
//        Mockito.doAnswer(answer).when(mockMainService).ServiceStatus(Mockito.anyString(),Mockito.any());
//

//        Status testStatus = new Status();
        postStatusPresenterSpy.PostStatus(Mockito.any());

        Mockito.verify(mockView).displayMessage("Successfully Posted!");
        Mockito.verify(mockView).PostStatusSuccessful();

    }

    @Test
    public void testPostStatus_postFailure(){
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.NotificationObserver observer = invocation.getArgument(1,MainService.NotificationObserver.class);//index 1 is the second argument hence the observer
                observer.handleFailure("the error message");
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockMainService).ServiceStatus(Mockito.any(),Mockito.any(MainService.NotificationObserver.class));

        postStatusPresenterSpy.PostStatus(Mockito.any());

        Mockito.verify (mockView).displayErrorMessage("Failed to post status: the error message");

    }

    @Test
    public void testPostStatus_postException(){
        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.NotificationObserver observer = invocation.getArgument(1,MainService.NotificationObserver.class);//index 1 is the second argument hence the observer
                observer.handleException(new Exception("the exception message"));
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockMainService).ServiceStatus(Mockito.any(),Mockito.any(MainService.NotificationObserver.class));

        postStatusPresenterSpy.PostStatus(Mockito.any());

        Mockito.verify (mockView).displayErrorMessage("Failed to post status because of exception: the exception message");

    }












}
