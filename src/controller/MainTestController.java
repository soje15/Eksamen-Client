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
public class MainTestController {


    private Service service;
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private String username;
    private String password;


    public MainTestController(Service service, Scanner inputReader, ViewHandler viewHandler) {
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


        System.out.println("Type in your CBS mail");
        username = inputReader.next();


        System.out.println("Type in your password");
        password = inputReader.next();


        try {

            service.Login(username, password, new ResponseCallback<UserDTO>() {
                public void success(UserDTO user) {

                    if (user != null) {
                        System.out.println("User type: " + user.getType());
                        if (user.getType().equals("student")) {
                            System.out.println("Loading user menu");

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            UserView userView = new UserView(service, user, inputReader, viewHandler);
                            viewHandler.setUserView(userView);
                            viewHandler.getUserView().userMenu();

                        } else if (user.getType().equals("admin")) {
                            System.out.println("Loading Admin Menu");

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            AdminView adminView = new AdminView(service, user, inputReader, viewHandler);
                            viewHandler.setAdminView(adminView);
                            viewHandler.getAdminView().AdminMenu();

                        } else if (user.getType().equals("teacher")){
                            System.out.println("Loading Teacher Menu");


                            TeacherView teacherView = new TeacherView(service, user, inputReader, viewHandler);
                            viewHandler.setTeacherView(teacherView);
                            teacherView.teacherMenu();

                        }

                    } else {
                        System.out.println("forkert login");
                    }

                }

                public void error(int status) {
                    System.out.println(status);

                }
            });
        } catch (Exception e) {
           e.printStackTrace();
            login();
        }


    }


}

