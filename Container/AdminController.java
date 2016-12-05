package controller;

import sdk.connection.ResponseCallback;
import sdk.models.*;
import sdk.service.Service;

import java.util.Scanner;


/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class AdminController {


    private Service service;
    private Scanner inputReader;
    private UserDTO user;

    public AdminController(Service service, UserDTO user, Scanner inputReader) {
        this.service = service;
        this.user = user;
        this.inputReader = inputReader;
     //   inputReader = new Scanner(System.in);

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

                Scanner inputReader2 = new Scanner(System.in);
                ReviewDTO deleteReview = new ReviewDTO();

                System.out.println("Type in the ID of the review, you wish to delete:");
                int deleteReviewID = inputReader.nextInt();
                deleteReview.setId(deleteReviewID);

                System.out.println("Type in the ID of the user, who owns the review:");
                int userID = inputReader.nextInt();
                deleteReview.setUserId(userID);


                service.updateReview(deleteReview, new ResponseCallback<Boolean>() {
                    public void success(Boolean data) {
                        System.out.println(data);
                        System.out.println("ReviewDTO softdeleted");
                    }

                    public void error(int status) {
                        System.out.println(status);

                    }
                });




                TestMenu();
                break;

            case 2:

                //Scanner inputReader = new Scanner(System.in);

                System.out.println("Type in the ID of the review");
                int reviewID = inputReader.nextInt();

                System.out.println("Type in the ID of the user");
                int userReviewID = inputReader.nextInt();

                ReviewDTO deleteReviewComment = new ReviewDTO();

                deleteReviewComment.setId(reviewID);
                deleteReviewComment.setUserId(userReviewID);

                service.deleteReviewComment(deleteReviewComment, new ResponseCallback<Boolean>() {
                    public void success(Boolean data) {
                        System.out.println("Sucessfully deleted comment");
                    }

                    public void error(int status) {
                        System.out.println("An error has occured: " + status);

                    }
                });


                break;


            case 3:
                System.out.println();
                user = null;
                //MainController mainMenuView = new MainController(service);
               // mainMenuView.MainMenu();
                break;

            default:
                System.out.println("Default");

                break;
        }

    }

    }

