package sdk.services;

/**
 * Created by sorenkolbyejensen on 18/11/2016.
 */

import sdk.models.User;

public class LoginService {

    private static User loginToken;

    public static void setAccessToken(User token){
        loginToken = token;
    }

    public static User getAccessToken(){
        return loginToken;
    }
    public static void clear(){
        loginToken = null;
    }

}