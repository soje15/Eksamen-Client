package view;

import controller.AdminController;
import controller.ViewHandler;
import sdk.models.*;
import sdk.service.Service;

import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Created by sorenkolbyejensen on 15/11/2016.
 * Our AdminView class contains the fundamental "backbone" of the
 * TUI.
 *
 */
public class AdminView {


    private Service service;
    private ViewHandler viewHandler;
    private AdminController adminController;
    private UserDTO user;

    /**
     *
     * @param service
     * @param user
     * @param viewHandler
     */
    public AdminView(Service service, UserDTO user, ViewHandler viewHandler) {
        this.service = service;
        this.user = user;
        this.viewHandler = viewHandler;


        AdminController adminController = new AdminController(service, user, viewHandler);
        this.adminController = adminController;
    }


    /**
     *
     *
     */
    public void adminMenu() {

        Scanner inputReader = new Scanner(System.in);

            System.out.println("====== ADMIN MENU =======");
            System.out.println("");
            System.out.println("(1) - Delete any review");
            System.out.println("(2) - Delete any review comment");
            System.out.println("(3) - log out");
        try {

            int choice = inputReader.nextInt();

            switch (choice) {
                case 1:

                    adminController.DeleteReview();


                    break;

                case 2:
                    adminController.DeleteReviewComment();


                    break;


                case 3:
                    user = null;
                    adminController = null;
                    viewHandler.getMainView().mainMenu();

                    break;

                default:
                    System.out.println("Default");

                    break;
            }

        } catch (InputMismatchException e) {
            System.out.println("Type in a valid input");
            adminMenu();
        }

    }}

