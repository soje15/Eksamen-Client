package sdk.connection;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 * ResponseParser is used to parse data from and to client from the server.
 */
public interface ResponseParser {

    /**
     *payload method, containing the JSON string from a sucessful response.
     * @param json
     */
    void payload(String json);

    /**
     *error method, containing status code from an unsucessful response.
     * @param status
     */

  void error(int status);

}
