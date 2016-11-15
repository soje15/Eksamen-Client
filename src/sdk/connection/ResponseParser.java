package sdk.connection;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */
public interface ResponseParser {

    void payload(String json);

    void error(int status);

}
