package controller;

import com.sun.tools.doclets.internal.toolkit.util.DocFinder;
import sdk.connection.ResponseCallback;
import sdk.models.UserDTO;
import sdk.service.Service;
import view.*;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class MainController {


    private Service service;
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private String username;
    private String password;


    public MainController(Service service, Scanner inputReader, ViewHandler viewHandler) {
        this.service = service;
        this.inputReader = inputReader;
        this.viewHandler = viewHandler;
    }

    /**
     * Login method, which loads either the user or admin menu upon succesful
     * login
     */

    public void login() {
        final UserDTO user = new UserDTO();


        /*
        Type in login information, to pass it to server to be checked in the database.
         */
        System.out.println("Type in your CBS mail");
        username = inputReader.next();


        System.out.println("Type in your password");
        password = inputReader.next();


        try {

            //Call service login method, with the login information. Implementing ResponesCallBack interface,
            //To handle callbacks.
            service.loginUser(username, password, new ResponseCallback<UserDTO>() {

                //On sucessful callback, check the user type and run the appropriate view.
                public void success(UserDTO user) {

                    //If user is not empty, attempt to get the type.
                    if (user != null) {
                        System.out.println("User type: " + user.getType());
                        if (user.getType().equals("student")) {
                            System.out.println("Loading user menu");


                            //Creating userview and running it's menu.
                            UserView userView = new UserView(service, user, inputReader, viewHandler);
                            viewHandler.setUserView(userView);
                            viewHandler.getUserView().userMenu();

                            //If the user is admin, run admin menu.
                        } else if (user.getType().equals("admin")) {
                            System.out.println("Loading Admin Menu");

                            AdminView adminView = new AdminView(service, user, inputReader, viewHandler);
                            viewHandler.setAdminView(adminView);
                            viewHandler.getAdminView().AdminMenu();


                            //If the user is teacher, run teacher menu
                        } else if (user.getType().equals("teacher")){
                            System.out.println("Loading Teacher Menu");


                            TeacherView teacherView = new TeacherView(service, user, inputReader, viewHandler);
                            viewHandler.setTeacherView(teacherView);
                            teacherView.teacherMenu();

                        }

                        //If the response is sucessful, but the server could not find the user
                        //print out "wrong login"
                    } else {
                        System.out.println("Wrong username/password");
                        login();
                    }

                }

                //If Response was unsucessful, return HTTP status code.
                public void error(int status) {
                    System.out.println("HTTP error: " + status);

                }
            });


        } catch (Exception e) {
           e.printStackTrace();
            login();
        }


    }


}

