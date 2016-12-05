package sdk.connection;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */
public interface ResponseCallback<T> {

        //Interface - generisk, Kan ikke hardcodes som arraylist.
        //Java generics, hedder T. Brugeren kan selv definere, hvad T skal være.

    /**
     *
     * @param data
     */


    void success(T data);

    /**
     *
     * @param status
     */

    void error(int status);

}
