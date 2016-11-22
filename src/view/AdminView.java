package view;

import sdk.connection.ResponseCallback;
import sdk.models.*;
import sdk.services.Service;
import sun.applet.Main;

import java.util.Scanner;


/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class AdminView {


    private Service service;
    private Scanner inputReader;
    private User user;

    public AdminView(Service service, User user) {
        this.service = service;
        this.user = user;

    }


 public void TestMenu(){

     System.out.println("====== ADMIN MENU =======");
     System.out.println("");
     System.out.println("(1) - Delete any review");
     System.out.println("(2) - log out");

        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1:

                Scanner inputReader2 = new Scanner(System.in);
                Review deleteReview = new Review();

                System.out.println("Type in the ID of the review, you wish to delete:");
                int deleteReviewID = inputReader2.nextInt();
                deleteReview.setId(deleteReviewID);

                System.out.println("Type in the ID of the user, who owns the review:");
                int userID = inputReader2.nextInt();
                deleteReview.setUserId(userID);


                service.updateReview(deleteReview, new ResponseCallback<Boolean>() {
                    public void success(Boolean data) {
                        System.out.println(data);
                        System.out.println("Review softdeleted");
                    }

                    public void error(int status) {
                        System.out.println(status);

                    }
                });




                TestMenu();
                break;

            case 2:
                System.out.println();
                user = null;
                MainMenuView mainMenuView = new MainMenuView(service);
                mainMenuView.MainMenu();
                break;

            default:
                System.out.println("Default");

                break;
        }

    }

    }

