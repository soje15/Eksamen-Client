package sdk.connection;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */
public interface ResponseParser {

    /**
     *
     * @param json
     */
    void payload(String json);

    /**
     *
     * @param status
     */

    void error(int status);

}
