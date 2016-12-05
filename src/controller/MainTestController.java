package controller;

import sdk.connection.ResponseCallback;
import sdk.models.UserDTO;
import sdk.service.Service;
import view.*;

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

    /***

     */

    public void login() {
        final UserDTO user = new UserDTO();


        System.out.println("Type in your CBS mail");
        username = inputReader.next();


        System.out.println("Type in your password");
        password = inputReader.next();


        try {

            service.authLogin(username, password, new ResponseCallback<UserDTO>() {
                public void success(UserDTO data) {

                    if (data != null) {
                        System.out.println("UserDTO type: " + data.getType());
                        if (data.getType().equals("student")) {
                            System.out.println("Loading UserDTO menu");

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            user.setCbsMail(data.getCbsMail());
                            user.setType(data.getType());

                            UserView userView = new UserView(service, data, inputReader, viewHandler);
                            viewHandler.setUserView(userView);
                            viewHandler.getUserView().userMenu();

                        } else if (data.getType().equals("admin")) {
                            System.out.println("Loading Admin UserDTO" + user.getType());

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            user.setCbsMail(data.getCbsMail());
                            user.setType(data.getType());

                            AdminView adminView = new AdminView(service, user, inputReader, viewHandler);
                            viewHandler.setAdminView(adminView);
                            viewHandler.getAdminView().TestMenu();
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
            System.out.println(e);
        }


    }


}

