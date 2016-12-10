package controller;

import sdk.connection.ResponseCallback;
import sdk.models.*;
import sdk.service.Service;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */

/**
 *This controller contains methods needed for the admin to delete reviews on the client.
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
     *Method to delete any review based one UserID and ReviewID
     */
    public void DeleteReview() {

service.getAllReviewsInclUserId(new ResponseCallback<ArrayList<ReviewDTO>>() {

    public void success(ArrayList<ReviewDTO> data) {
        for (ReviewDTO reviews: data){
            System.out.println();
            System.out.println("Review comment: " + reviews.getComment());
            System.out.println("Review Rating: " + reviews.getRating());
            System.out.println("Lecture ID : " + reviews.getLectureId());
            System.out.println("Review ID: " + reviews.getId());
            System.out.println("User ID" + reviews.getUserId());

        }
    }

    public void error(int status) {
        System.out.println("HTTP error status: " + status);
        viewHandler.getAdminView().AdminMenu();
    }
});

        Scanner inputReader2 = new Scanner(System.in);
        ReviewDTO deleteReview = new ReviewDTO();

        System.out.println("Type in the ID of the review, you wish to delete:");
        int deleteReviewID = inputReader.nextInt();
        deleteReview.setId(deleteReviewID);

        System.out.println("Type in the ID of the user, who owns the review:");
        int userID = inputReader.nextInt();
        deleteReview.setUserId(userID);


        /**
         * Review is "updated" in the sense that it is softdeleted.
         * By switching a softdelete integer to 1.
         */
        service.updateReview(deleteReview, new ResponseCallback<Boolean>() {
            public void success(Boolean data) {
                System.out.println(data);
                System.out.println("ReviewDTO softdeleted");
            }

            public void error(int status) {
                System.out.println(status);
                viewHandler.getAdminView().AdminMenu();

            }
        });


        viewHandler.getAdminView().AdminMenu();


    }

    /**
     *This method will delete any reviews comment, based on the userid and the id of the user.
     */
    public void DeleteReviewComment() {

        service.getAllReviewsInclUserId(new ResponseCallback<ArrayList<ReviewDTO>>() {

            public void success(ArrayList<ReviewDTO> data) {
                for (ReviewDTO reviews: data){
                    System.out.println();
                    System.out.println("Review comment: " + reviews.getComment());
                    System.out.println("Review Rating: " + reviews.getRating());
                    System.out.println("Lecture ID : " + reviews.getLectureId());
                    System.out.println("Review ID: " + reviews.getId());
                    System.out.println("User ID" + reviews.getUserId());

                }
            }

            public void error(int status) {
                System.out.println("HTTP error status: " + status);
                viewHandler.getAdminView().AdminMenu();
            }
        });


        System.out.println("Type in the ID of the review");
        int reviewID = inputReader.nextInt();

        System.out.println("Type in the ID of the user");
        int userReviewID = inputReader.nextInt();

        ReviewDTO deleteReviewComment = new ReviewDTO();

        deleteReviewComment.setId(reviewID);
        deleteReviewComment.setUserId(userReviewID);

        /**
         * Review is deleted and a boolean is returned
         */
        service.deleteReviewComment(deleteReviewComment, new ResponseCallback<Boolean>() {
            public void success(Boolean data) {
                System.out.println("Sucessfully deleted comment");
            }

            public void error(int status) {
                System.out.println("An error has occured: " + status);
                viewHandler.getAdminView().AdminMenu();

            }
        });

        viewHandler.getAdminView().AdminMenu();

    }


}
