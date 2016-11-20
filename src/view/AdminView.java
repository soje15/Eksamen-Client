package view;

import controller.ViewHandler;
import sdk.services.Service;

import java.util.Scanner;


/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class AdminView {


    private ViewHandler viewHandler;
    private Service service;
    private Scanner inputReader;

    public AdminView(ViewHandler viewHandler, Service service) {
        this.viewHandler = viewHandler;
        this.service = service;

    }


 public static void TestMenu(){

     System.out.println("loaded");
        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1:

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
                break;

            default:
                System.out.println("Default");

                break;
        }

    }

    }

