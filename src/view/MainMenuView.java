package view;

import controller.ViewHandler;
import sdk.connection.ResponseCallback;
import sdk.models.Lecture;
import sdk.services.Service;

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

            TestView testView = new TestView(viewHandler,service);
            UserView userView = new UserView(viewHandler,service);
            this.testView = testView;
            this.userView = userView;

            MainMenu();
        }

    public void MainMenu() {

            System.out.println("Main menu");
            System.out.println("(1) - Go to Userview");
            System.out.println("(2) - Go to TestView");
            System.out.println("(3) - Get all Lectures");
            System.out.println("(4) - ");
            System.out.println();
            System.out.println("(5) - Shut down");

            Scanner inputReader = new Scanner(System.in);
            int choice = inputReader.nextInt();

            switch (choice) {

                case 1:
                    this.userView.userMenu();

                    break;

                case 2:

                    Scanner inputReader2 = new Scanner(System.in);
                    String LectureID = inputReader.next();


                    service.getAll(LectureID, new ResponseCallback<ArrayList<Lecture>>() {

                        public void success(ArrayList<Lecture> data) {

                            System.out.println("success");
                        }

                        public void error(int status) {

                            System.out.println(status);
                        }
                    });

                case 3:

                    break;

                case 4:

                    //this.viewHandler.getUserView().presentMenu();
                    break;

                case 5:


                default:
                   MainMenu();
                    break;
            }
        }


    }

