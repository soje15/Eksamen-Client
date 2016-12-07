package view;

import controller.AdminTestController;
import controller.ViewHandler;
import sdk.models.*;
import sdk.service.Service;

import java.util.Scanner;


/**
 * Created by sorenkolbyejensen on 15/11/2016.
 * Our AdminView class contains the fundamental "backbone" of the
 * TUI.
 *
 */
public class AdminView {


    private Service service;
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private AdminTestController adminTestController;
    private UserDTO user;

    /**
     *
     * @param service
     * @param user
     * @param inputReader
     * @param viewHandler
     */
    public AdminView(Service service, UserDTO user, Scanner inputReader, ViewHandler viewHandler) {
        this.service = service;
        this.user = user;
        this.inputReader = inputReader;
        //   inputReader = new Scanner(System.in);

        this.viewHandler = viewHandler;
        AdminTestController adminTestController = new AdminTestController(service, user, inputReader, viewHandler);
        this.adminTestController = adminTestController;
    }


    public void TestMenu(){

        System.out.println("====== ADMIN MENU =======");
        System.out.println("");
        System.out.println("(1) - Delete any review");
        System.out.println("(2) - Delete any review comment");
        System.out.println("(3) - log out");
        int choice = inputReader.nextInt();

        switch (choice) {
            case 1:

                adminTestController.DeleteReview();


                break;

            case 2:
                adminTestController.DeleteReviewComment();



                break;


            case 3:
                user = null;
                adminTestController = null;
                viewHandler.getMainView().MainMenu();

                break;

            default:
                System.out.println("Default");

                break;
        }

    }

}

