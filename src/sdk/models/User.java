package sdk.models;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */
public class User{

    private String cbsMail;
    private String password;

    public User() {
    }


    public String getUsername() {
        return cbsMail;
    }

    public void setUsername(String username) {
        this.cbsMail = cbsMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
