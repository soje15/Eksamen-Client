package sdk.models;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 * Our UserdTO, containing variables which mirrors the server.
 */
public class UserDTO {

    private int id;
    private String cbsMail;
    private String password;
    private String type;

    public UserDTO() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCbsMail() {
        return cbsMail;
    }

    public void setCbsMail(String cbsMail) {
        this.cbsMail = cbsMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
