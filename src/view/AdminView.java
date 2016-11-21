package view;

import sdk.services.Service;

import java.util.Scanner;


/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class AdminView {


    private Service service;
    private Scanner inputReader;

    public AdminView(Service service) {
        this.service = service;

    }


 public static void TestMenu(){

     System.out.println("====== ADMIN MENU =======");
     System.out.println("");
     System.out.println("(1) - Delete any review");
     System.out.println("(4) - log out");

        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1:
                System.out.println("Delete review here");

                TestMenu();
                break;

            case 2:
                System.out.println("Input Lecture ID:");
                String id = new Scanner(System.in).nextLine();

                break;

            case 3:
                System.out.println("Create new Lecture:");

                break;

            case 4:
                // this.viewHandler.showMainMenu();
                System.exit(0);
                break;

            default:
                System.out.println("Default");

                break;
        }

    }

    }

