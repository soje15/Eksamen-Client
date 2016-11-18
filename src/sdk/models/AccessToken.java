package sdk.models;

/**
 * Created by sorenkolbyejensen on 18/11/2016.
 */
public class AccessToken {

    private String id;
    private String userId;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}