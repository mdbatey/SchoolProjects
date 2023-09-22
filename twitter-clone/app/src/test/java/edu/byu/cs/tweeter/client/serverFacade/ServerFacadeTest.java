package edu.byu.cs.tweeter.client.serverFacade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.GetCountRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.GetCountResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class ServerFacadeTest {


    private ServerFacade serverFacadeSpy;
    private ServerFacade mockServerFacade;

    @BeforeEach
    public void setup() {
        mockServerFacade = Mockito.mock(ServerFacade.class);
        serverFacadeSpy = Mockito.spy(new ServerFacade());
    }

    @Test
    public void testRegister(){
        //todo

        try {
            RegisterRequest request = new RegisterRequest("first", "last", "@firstlast", "test", "blank");
            RegisterResponse response = serverFacadeSpy.register(request, "/register");


            System.out.println("response: " + response.getUser().getFirstName());
            assert response.isSuccess();
            assert response.getUser() != null;
            assert response.getUser().getFirstName().equals("Allen");
//            assert response.getUser().getLastName().equals("last");

        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
        }



    }

    @Test
    public void testGetFollowers(){
        //todo

        try{
            FollowersRequest request = new FollowersRequest(FakeData.getInstance().getAuthToken(), "@allen", 10, "@j");
            FollowersResponse response = serverFacadeSpy.getFollowers(request, "/getfollowers");

            assert response.isSuccess();
            assert response.getFollowers() != null; // Todo make sure to test correct group of followers

        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFollowingCount (){

        try{
            GetCountRequest request = new GetCountRequest("@allen", 2);
            GetCountResponse response = serverFacadeSpy.getFollowingCount(request, "/getfollowingcount");

            assert response.isSuccess();
            assert response.getCount() != 0;


        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFollowersCount (){
        try{
            GetCountRequest request = new GetCountRequest("@allen", 2);
            GetCountResponse response = serverFacadeSpy.getFollowingCount(request, "/getfollowingcount");

            assert response.isSuccess();
            assert response.getCount() != 0;


        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
        }
    }


}
