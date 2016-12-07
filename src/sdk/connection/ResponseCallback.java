package sdk.connection;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 *ResponseCallBack is used to handle the response from the server
 * in the ConnectionImpl class.
 *
 */
public interface ResponseCallback<T> {


    /**
     *Sucess function takes any generic data-type, which can be defined as you wish.
     * @param data
     */
    void success(T data);

    /**
     *error function returns our status code from our response, as defined in "ConnectionImpl".
     * @param status
     */

    void error(int status);

}
