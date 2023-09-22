package edu.byu.cs.tweeter.server.dao;

import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.factory.AuthtokenModel;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class AuthtokenDAO extends DynamoDAO implements AuthInterface {

    private static final String TableName = "Authtoken";

//    private static final String authtoken = "authToken";


    // DynamoDB client
    private static DynamoDbClient dynamoDbClient = getDynamoDbClient();

    private static DynamoDbEnhancedClient enhancedClient = getEnhancedClient();


    public boolean checkAuthTokenTime(String authToken) {
        DynamoDbTable<AuthtokenModel> table = enhancedClient.table(TableName, TableSchema.fromBean(AuthtokenModel.class));
        Key key = Key.builder()
                .partitionValue(authToken)
                .build();

        AuthtokenModel authtokenModel = table.getItem(key);

        if (authtokenModel == null) {
            return false;
        }

        long timestamp = Long.parseLong(authtokenModel.getTimestamp());
        long currentTime = System.currentTimeMillis();

        //check if time is greater than 12hours
        if (currentTime - timestamp > 43200000) {
            return false;
        }

//        // check if time is greater than 15 minute
//        if (currentTime - timestamp > 900000) {
//            return false;
//        }

//        // check if time is greater than 1 minute
//        if (currentTime - timestamp > 60000) {
//            return false;
//        }

        return true;
    }

//    public AuthToken getAuthToken(String token) {
//        DynamoDbTable<AuthtokenModel> table = enhancedClient.table(TableName, TableSchema.fromBean(AuthtokenModel.class));
//        Key key = Key.builder()
//                .partitionValue(token)
//                .build();
//
//        AuthtokenModel authtokenModel = table.getItem(key);
//
//        if (authtokenModel == null) {
//            return null;
//        } else if (!checkAuthTokenTime(token)) {
//            return null;
//        }
//
//        return new AuthToken(authtokenModel.getAuth_token(), authtokenModel.getTimestamp());
//    }

    public AuthToken createAuthToken(String alias) {
        DynamoDbTable<AuthtokenModel> table = enhancedClient.table(TableName, TableSchema.fromBean(AuthtokenModel.class));

        AuthtokenModel authtokenModel = new AuthtokenModel();

        authtokenModel.setAlias(alias);

        String authtoken = UUID.randomUUID().toString();
        authtokenModel.setAuth_token(authtoken);

        long timestamp = System.currentTimeMillis();
        authtokenModel.setTimestamp(Long.toString(timestamp));

        table.putItem(authtokenModel);


        return new AuthToken(authtoken, Long.toString(timestamp));
    }


    public void logoutUser(String authToken) {
        DynamoDbTable<AuthtokenModel> table = enhancedClient.table(TableName, TableSchema.fromBean(AuthtokenModel.class));
        Key key = Key.builder()
                .partitionValue(authToken)
                .build();


        table.deleteItem(key);
    }


}



