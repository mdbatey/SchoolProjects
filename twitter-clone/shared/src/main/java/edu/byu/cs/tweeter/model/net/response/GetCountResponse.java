package edu.byu.cs.tweeter.model.net.response;

public class GetCountResponse extends Response{

    private String userAlias;
    private int count;
    public GetCountResponse(boolean success) {
        super(success);
    }

    public GetCountResponse(boolean success, String message) {
        super(success, message);
    }

    public GetCountResponse(String userAlias, int count) {
        super(true);
        this.userAlias = userAlias;
        this.count = count;
    }

    public int getCount() {
        return count;
    }


    public String getUserAlias() {
        return userAlias;
    }

}
