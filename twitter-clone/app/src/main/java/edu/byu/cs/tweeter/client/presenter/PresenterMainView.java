package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

public interface PresenterMainView extends PresenterView{

        abstract void LogoutSuccessful();

        abstract void GetFollowersCountSuccessful(int count);

        abstract void GetFollowingCountSuccessful(int count);

        abstract void IsFollowerSuccessful(boolean isFollower);

        abstract void FollowSuccessful();

        abstract void PostStatusSuccessful();

        abstract void UnfollowSuccessful();

        abstract void setFollow(boolean value);
}
