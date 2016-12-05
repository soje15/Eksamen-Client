package controller;

import sdk.connection.ResponseCallback;
import sdk.models.*;
import sdk.service.Service;

import java.util.Scanner;


/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class AdminTestController {


    private Service service;
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private UserDTO user;

    public AdminTestController(Service service, UserDTO user, Scanner inputReader, ViewHandler viewHandler
    ) {
        this.service = service;
        this.user = user;
        this.inputReader = inputReader;


        this.viewHandler = viewHandler;
    }

    /**
     *
     */
    public void DeleteReview() {


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
                viewHandler.getAdminView().TestMenu();

            }
        });

        viewHandler.getAdminView().TestMenu();


    }

    /**
     *
     */
    public void DeleteReviewComment() {


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
                viewHandler.getAdminView().TestMenu();

            }
        });

        viewHandler.getAdminView().TestMenu();

    }


}
