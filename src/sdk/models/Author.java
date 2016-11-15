package sdk.models;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */

//
public class Author extends BaseModel {
    private String firstName;
    private String lastName;

    public Author() {
      //  this.getId();

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
