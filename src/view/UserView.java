package view;
import controller.ViewHandler;
import sdk.services.Service;

import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class UserView {

    private ViewHandler viewHandler;
    private Service service;
    private Scanner inputReader;

    public UserView(ViewHandler viewHandler, Service service) {
        this.viewHandler = viewHandler;
        this.service = new Service();

    }


    public void userMenu() {
        System.out.println("====== User menu ======");
        System.out.println();
        System.out.println("(1) - Show all users");
        System.out.println("(2) - Login");
        System.out.println("(3) - Show user by ID");
        System.out.println("(4) - Create new user");
        System.out.println();
        System.out.println("(5) - Back to main menu");
        System.out.println();

        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1:

                userMenu();
                break;

            case 2:

                userMenu();
                break;

            case 3:

                userMenu();
                break;

            case 4:

                System.out.println("Create new Lecture:");
                userMenu();
                break;

            case 5:

                break;

            default:
                System.out.println("Default");
                userMenu();
                break;
        }
    }






}

