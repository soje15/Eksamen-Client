package view;

import Encrypter.Digester;
import sdk.connection.ResponseCallback;
import sdk.models.User;
import sdk.services.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class MainMenuView {


        private Service service;
        public AdminView adminView;
        public UserView userView;



        public MainMenuView(Service service) {
            this.service = service;

            MainMenu();
        }

    public void MainMenu() {

            System.out.println("Main menu");
            System.out.println("(1) - Log in");
            System.out.println("(5) - Shut down");

        try {
            Scanner inputReader = new Scanner(System.in);
            int choice = inputReader.nextInt();


            switch (choice) {

                case 1:

                    final User user = new User();
                    //Scanner inputReaderUsername = new Scanner(System.in);

                    System.out.println("Type in your CBS mail");
                    final String username = inputReader.next();


                    System.out.println("Type in your password");
                    String password = inputReader.next();


                    String hashedPassword = Digester.hashWithSalt(password);
                    String doubleHashed = Digester.hashWithSalt(hashedPassword);

                    //System.out.println(doubleHashed);


                    try {

                        service.authLogin(username, doubleHashed, new ResponseCallback<User>() {
                            public void success(User data) {

                                if (data != null) {
                                    System.out.println("User type: " + data.getType());
                                    if (data.getType().equals("student") ) {
                                        System.out.println("Loading User menu");

                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        user.setCbsMail(data.getCbsMail());
                                        user.setType(data.getType());

                                        UserView userview = new UserView(service, data);
                                        userview.userMenu();
                                    } else if (data.getType().equals("admin")) {
                                        System.out.println("Loading Admin User" + user.getType());

                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        user.setCbsMail(data.getCbsMail());
                                        user.setType(data.getType());

                                        AdminView testview = new AdminView(service, user);
                                        testview.TestMenu();
                                    }


                                }else {
                                    System.out.println("forkert login");
                                    MainMenu();
                                }

                            }

                            public void error(int status) {
                                System.out.println(status);

                            }
                        });
                    }catch (Exception e){
                        System.out.println(e);
                    }


                    break;

                case 2:
                System.exit(0);


                default:
                    System.out.println("Default case");
                   MainMenu();
                    break;
            }        }catch  (InputMismatchException e) {
            System.out.println();
            System.out.println("indtast venligst et gyldigt tal");
            System.out.println();
        }

    }


    }

