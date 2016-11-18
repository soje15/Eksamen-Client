package view;

import Encrypter.Digester;
import controller.ViewHandler;
import sdk.connection.ResponseCallback;
import sdk.models.Lecture;
import sdk.models.User;
import sdk.services.Service;

import javax.swing.text.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class MainMenuView {


        private Service service;
        private ViewHandler viewHandler;
        public TestView testView;
        public UserView userView;



        public MainMenuView(ViewHandler viewHandler, Service service) {
            this.viewHandler = viewHandler;
            this.service = service;

            MainMenu();
        }

    public void MainMenu() {

            System.out.println("Main menu");
            System.out.println("(1) - Log in");
            System.out.println("(5) - Shut down");

            Scanner inputReader = new Scanner(System.in);
            int choice = inputReader.nextInt();

            switch (choice) {

                case 1:

                    final User user = new User();
                    Scanner inputReaderUsername = new Scanner(System.in);

                    System.out.println("Type in your CBS mail");
                    final String username = inputReader.next();


                    System.out.println("Type in your password");
                    String password = inputReader.next();


                    String hashedPassword = Digester.hashWithSalt(password);
                    String doubleHashed = Digester.hashWithSalt(hashedPassword);

                    System.out.println(doubleHashed);


                    try {

                        service.authLogin(username, doubleHashed, new ResponseCallback<User>() {
                            public void success(User data) {

                                if (data != null) {
                                    user.setCbsMail(data.getCbsMail());
                                    user.setType(data.getType());

                                    UserView userview = new UserView(viewHandler, service, data);
                                    userview.userMenu();
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
            }
        }


    }

