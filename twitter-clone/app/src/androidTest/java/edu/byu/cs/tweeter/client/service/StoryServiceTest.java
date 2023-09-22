package edu.byu.cs.tweeter.client.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

public class StoryServiceTest {

    private User currentUser;
    private AuthToken currentAuthToken;

    private StatusService storyServiceSpy;
    private StoryTestObserver observer;
    private CountDownLatch countDownLatch;



    /**
     * Create a StoryService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        currentUser = new User("FirstName", "LastName", null);
        currentAuthToken = new AuthToken();

        storyServiceSpy = Mockito.spy(new StatusService());

        // Setup an observer for the StoryService
        observer = new StoryTestObserver();

        // Prepare the countdown latch
        resetCountDownLatch();
    }

    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }

    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
    }

    
    private class StoryTestObserver implements StatusService.PageStatusObserver {


        private boolean success;
        private String message;
        private List<Status> statuses;
        private boolean hasMorePages;
        private Exception exception;

        @Override
        public void handleFailure(String message) {
            this.success = false;
            this.message = message;
            this.statuses = null;
            this.hasMorePages = false;
            this.exception = null;
            countDownLatch.countDown();
        }

        @Override
        public void handleException(Exception ex) {
            this.success = false;
            this.message = null;
            this.statuses = null;
            this.hasMorePages = false;
            this.exception = exception;

            countDownLatch.countDown();
        }

        @Override
        public void handleSuccess(List<Status> items, Boolean hasMorePages) {
            this.success = true;
            this.message = null;
            this.statuses = statuses;
            this.hasMorePages = hasMorePages;
            this.exception = null;

            countDownLatch.countDown();
        }



        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public List<Status> getStatuses() {
            return statuses;
        }

        public boolean getHasMorePages() {
            return hasMorePages;
        }

        public Exception getException() {
            return exception;
        }
    }


    @Test
    public void testGetFollowees_validRequest_correctResponse() throws InterruptedException {
        //        storyServiceSpy.getFollowees(currentAuthToken, currentUser, 3, null, observer);
        storyServiceSpy.LoadStory(currentUser, 3, null, observer);
        awaitCountDownLatch();

        List<User> expectedFollowees = FakeData.getInstance().getFakeUsers().subList(0, 3);
        Assertions.assertTrue(observer.isSuccess());
        Assertions.assertNull(observer.getMessage());
        Assertions.assertEquals(expectedFollowees, observer.getStatuses());
        Assertions.assertTrue(observer.getHasMorePages());
        Assertions.assertNull(observer.getException());
    }


    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws InterruptedException {
        storyServiceSpy.LoadStory(currentUser, 3, null, observer);
        awaitCountDownLatch();

        List<Status> statuses = observer.getStatuses();
        Assertions.assertTrue(statuses.size() > 0);
    }


    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws InterruptedException {
        storyServiceSpy.LoadStory(null, 0, null, observer);
        awaitCountDownLatch();

        Assertions.assertFalse(observer.isSuccess());
        Assertions.assertNull(observer.getMessage());
        Assertions.assertNull(observer.getStatuses());
        Assertions.assertFalse(observer.getHasMorePages());
        Assertions.assertNotNull(observer.getException());
    }






}
