package logic;

import encrypter.Digester;
import sdk.connection.ResponseCallback;
import sdk.models.User;
import sdk.service.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class MainController {


        private Service service;
        private Scanner inputReader;
        private String username;
        private String password;




        public MainController(Service service, Scanner inputReader) {
            this.service = service;
            this.inputReader = inputReader;
            //inputReader = new Scanner(System.in);

            MainMenu();
        }

    public void MainMenu() {

            System.out.println("Main menu");
            System.out.println("(1) - Log in");
            System.out.println("(5) - Shut down");

        try {
            int choice = inputReader.nextInt();


            switch (choice) {

                case 1:

                    final User user = new User();


                    System.out.println("Type in your CBS mail");
                    username = inputReader.next();


                    System.out.println("Type in your password");
                    password = inputReader.next();


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

                                        UserController userController = new UserController(service, data, inputReader);
                                        userController.userMenu();
                                    } else if (data.getType().equals("admin")) {
                                        System.out.println("Loading Admin User" + user.getType());

                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        user.setCbsMail(data.getCbsMail());
                                        user.setType(data.getType());

                                        AdminController testview = new AdminController(service, user, inputReader);
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

