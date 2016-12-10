package view;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import controller.MainTestController;
import controller.ViewHandler;
import sdk.service.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class MainView {


    private Service service;
    private MainTestController maintestController;
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private MainTestController mainTestController;
    private String username;
    private String password;


    /**
     *
     * @param service
     * @param inputReader
     * @param viewHandler
     */
    public MainView(Service service, Scanner inputReader, ViewHandler viewHandler) {
        this.service = service;
        this.inputReader = inputReader;
        this.viewHandler = viewHandler;

        MainTestController maintestController = new MainTestController(service, inputReader, viewHandler);
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
