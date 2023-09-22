package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.server.factory.UsersModel;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class UserDAO extends DynamoDAO implements UserInterface {


    private static final String TableName = "User";

    private static final String alias = "alias";


    // DynamoDB client
    private static DynamoDbClient dynamoDbClient = getDynamoDbClient();

    private static DynamoDbEnhancedClient enhancedClient = getEnhancedClient();



    public int getFollowingCount(String alias){
        DynamoDbTable<UsersModel> table = enhancedClient.table(TableName, TableSchema.fromBean(UsersModel.class));
        Key key = Key.builder()
                .partitionValue(alias)
                .build();

        UsersModel usersModel = table.getItem(key);

        return usersModel.getFollowingCount();
    }

    public void updateFollowingCount(String alias, int count){
        DynamoDbTable<UsersModel> table = enhancedClient.table(TableName, TableSchema.fromBean(UsersModel.class));
        Key key = Key.builder()
                .partitionValue(alias)
                .build();

        UsersModel usersModel = table.getItem(key);
        usersModel.setFollowingCount(count);
        table.putItem(usersModel);
    }

    public int getFollowersCount(String alias){
        DynamoDbTable<UsersModel> table = enhancedClient.table(TableName, TableSchema.fromBean(UsersModel.class));
        Key key = Key.builder()
                .partitionValue(alias)
                .build();

        UsersModel usersModel = table.getItem(key);

        return usersModel.getFollowersCount();
    }

    public void updateFollowersCount(String alias, int count){
        DynamoDbTable<UsersModel> table = enhancedClient.table(TableName, TableSchema.fromBean(UsersModel.class));
        Key key = Key.builder()
                .partitionValue(alias)
                .build();

        UsersModel usersModel = table.getItem(key);
        usersModel.setFollowersCount(count);
        table.putItem(usersModel);
    }



    public User getUser(String username) {

        DynamoDbTable<UsersModel> table = enhancedClient.table(TableName, TableSchema.fromBean(UsersModel.class));
        Key key = Key.builder()
                .partitionValue(username)
                .build();

        UsersModel usersModel = table.getItem(key);
        if(usersModel != null) {
            return new User(usersModel.getFirstName(), usersModel.getLastName(), usersModel.getAlias(), usersModel.getImageUrl());
        } else {
            return null;
        }
    }

    public boolean loginUser(LoginRequest request){
        DynamoDbTable<UsersModel> table = enhancedClient.table(TableName, TableSchema.fromBean(UsersModel.class));
        Key key = Key.builder()
                .partitionValue(request.getUsername())
                .build();

        UsersModel usersModel = table.getItem(key);
        String hashedPassword = hashPassword(request.getPassword());

        if (hashedPassword.equals(usersModel.getPassword())) {
//            System.out.println("Login Successful");
            return true;
        } else {
//            System.out.println("Login Failed");
            return false;
        }



    }


    public void registerNewUser(RegisterRequest request) {
        DynamoDbTable<UsersModel> table = enhancedClient.table(TableName, TableSchema.fromBean(UsersModel.class));

        UsersModel newUser = new UsersModel();
        newUser.setAlias(request.getUsername());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());

        imageToS3(request.getUsername(), request.getImageURL());
        newUser.setImageUrl("https://2023-340-bucket.s3.us-west-2.amazonaws.com/" + request.getUsername());

        String hashedPassword = hashPassword(request.getPassword());
        newUser.setPassword(hashedPassword);

        newUser.setFollowersCount(0);
        newUser.setFollowingCount(0);



        table.putItem(newUser);
//        Key key = Key.builder()
//                .partitionValue(alias)
//                .build();



//        User user = table.getItem(key);
//        if(user != null) {
////            user.setFollower_name(user.getFollower_name());
//            user.setFirstName(user.getFirstName());
//            user.setLastName(user.getLastName());
//            user.setImageUrl(user.getImageUrl());
//            table.updateItem(user);
//        } else {
//            User newUser = new User(user.getFirstName(),user.getLastName(), user.getAlias(), user.getImageUrl());
//            table.putItem(newUser);
//        }


    }







    private static String hashPassword(String passwordToHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "FAILED TO HASH";
    }


    private void imageToS3(String alias, String imageURL) {
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        byte[] byteArray = Base64.getDecoder().decode(imageURL);

        ObjectMetadata data = new ObjectMetadata();

        data.setContentLength(byteArray.length);

        data.setContentType("image/jpeg");

        PutObjectRequest request = new PutObjectRequest("2023-340-bucket", alias,
                new ByteArrayInputStream(byteArray), data).withCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(request);
    }


}
