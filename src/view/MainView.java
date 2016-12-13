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
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private MainController mainController;


    /**
     * Constructor, initializing our variables.
     *
     * @param service
     * @param inputReader
     * @param viewHandler
     */
    public MainView(Service service, Scanner inputReader, ViewHandler viewHandler) {
        this.service = service;
        this.inputReader = inputReader;
        this.viewHandler = viewHandler;

        MainController maintestController = new MainController(service, inputReader, viewHandler);
        this.maintestController = maintestController;
    }


    /**
     * MainMenu - case switch to choose.
     *
     */
    public void MainMenu() {

        System.out.println("Main menu");
        System.out.println("(1) - Log in");
        System.out.println("(2) - Shut down");

        try {
            int choice = inputReader.nextInt();


            switch (choice) {

                case 1:
                    maintestController.login();


                    break;

                case 2:
                    System.exit(0);

            }
        }catch  (InputMismatchException e) {

            System.out.println("Please type in a valid input.");
           MainMenu();
        }
    }
}
