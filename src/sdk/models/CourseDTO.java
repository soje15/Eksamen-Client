package sdk.models;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 * Our CourseDTO, containing variables which mirrors the server.
 */

//
public class CourseDTO {

    private String id;
    private String code;
    private String displaytext;

    public CourseDTO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplaytext() {
        return displaytext;
    }

    public void setDisplaytext(String displaytext) {
        this.displaytext = displaytext;
    }
}
