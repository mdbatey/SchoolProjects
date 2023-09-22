package edu.byu.cs.tweeter.server.dao;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDAO {

    // DynamoDB client
    private static DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
            .region(Region.US_WEST_2)
            .build();

    private static DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();

    public static DynamoDbClient getDynamoDbClient() {
        return dynamoDbClient;
    }

    public static DynamoDbEnhancedClient getEnhancedClient() {
        return enhancedClient;
    }

    public static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }
}
