package view;

import controller.MainController;
import controller.ViewHandler;
import sdk.service.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class MainView {


    private Service service;
    private MainController maintestController;
    private ViewHandler viewHandler;
    private MainController mainController;


    /**
     * Constructor, initializing our variables.
     *
     * @param service
     * @param viewHandler
     */
    public MainView(Service service, ViewHandler viewHandler) {
        this.service = service;
        this.viewHandler = viewHandler;

        MainController maintestController = new MainController(service, viewHandler);
        this.maintestController = maintestController;
    }


    /**
     * MainMenu - case switch to choose.
     */
    public void mainMenu() {
        Scanner input = new Scanner(System.in);

        try {
        System.out.println("Main menu");
        System.out.println("(1) - Log in");
        System.out.println("(2) - Shut down");

            int choice = input.nextInt();

            switch (choice) {


                case 1:
                    maintestController.login();


                    break;

                case 2:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Something went wrong");
                    mainMenu();
                    break;
            }

        }catch  (InputMismatchException e) {

            mainMenu();
            System.out.println("Please type in a valid input.");
        }
    }
}