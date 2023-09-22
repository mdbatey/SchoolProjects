package edu.byu.cs.tweeter.server.factory;

import edu.byu.cs.tweeter.server.dao.FollowDAO;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;


// added all the things for a user
@DynamoDbBean
public class Follows {
    private String follower_handle;
//    private String follower_name;
    private String follower_firstName;
    private String follower_lastName;
    private String follower_imageUrl;


    private String followee_handle;
//    private String followee_name;
    private String followee_firstName;
    private String followee_lastName;
    private String followee_imageUrl;

    @DynamoDbPartitionKey
    @DynamoDbSecondarySortKey(indexNames = FollowDAO.IndexName)
    public String getFollower_handle() {
        return follower_handle;
    }

    public void setFollower_handle(String follower) {
        this.follower_handle = follower;
    }

    @DynamoDbSortKey
    @DynamoDbSecondaryPartitionKey(indexNames = FollowDAO.IndexName)
    public String getFollowee_handle() {
        return followee_handle;
    }

    public void setFollowee_handle(String followee) {
        this.followee_handle = followee;
    }


//    public String getFollowee_name() {
//        return followee_name;
//    }
//
//    public void setFollowee_name(String followee_name) {
//        this.followee_name = followee_name;
//    }
//
//    public String getFollower_name() {
//        return follower_name;
//    }
//
//    public void setFollower_name(String follower_name) {
//        this.follower_name = follower_name;
//    }


    public String getFollower_firstName() {
        return follower_firstName;
    }

    public void setFollower_firstName(String follower_firstName) {
        this.follower_firstName = follower_firstName;
    }

    public String getFollower_lastName() {
        return follower_lastName;
    }

    public void setFollower_lastName(String follower_lastName) {
        this.follower_lastName = follower_lastName;
    }

    public String getFollower_imageUrl() {
        return follower_imageUrl;
    }

    public void setFollower_imageUrl(String follower_imageUrl) {
        this.follower_imageUrl = follower_imageUrl;
    }

    public String getFollowee_firstName() {
        return followee_firstName;
    }

    public void setFollowee_firstName(String followee_firstName) {
        this.followee_firstName = followee_firstName;
    }

    public String getFollowee_lastName() {
        return followee_lastName;
    }

    public void setFollowee_lastName(String followee_lastName) {
        this.followee_lastName = followee_lastName;
    }

    public String getFollowee_imageUrl() {
        return followee_imageUrl;
    }

    public void setFollowee_imageUrl(String followee_imageUrl) {
        this.followee_imageUrl = followee_imageUrl;
    }

    @Override
    public String toString() {
        return "\nFollows {" +
                " follower='" + follower_handle + '\'' +
                ", followee='" + followee_handle + '\'' +
//                ", follower_name='" + follower_name + '\'' +
//                ", followee_name='" + followee_name + '\'' +
                '}';
    }
}