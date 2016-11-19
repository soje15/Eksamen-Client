package view;
import controller.ViewHandler;
import sdk.connection.ResponseCallback;
import sdk.models.*;
import sdk.services.LoginService;
import sdk.services.Service;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class UserView {

    private ViewHandler viewHandler;
    private Service service;
    private Scanner inputReader;
    private User user;

    public UserView(ViewHandler viewHandler, Service service, User user) {
        this.viewHandler = viewHandler;
      this.service = service;
        this.user = user;

    }


    public void userMenu() {
        System.out.println("====== User menu ======");
        System.out.println();
        System.out.println("(1) - Show all lectures");
        System.out.println("(2) - Show attended courses");
        System.out.println("(3) - Add review to course");
        System.out.println("(4) - Show all reviews");
        System.out.println("(6) - Log out");
        System.out.println();

        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1:



                service.getAll("BALJO1001U_XJA_E16", new ResponseCallback<ArrayList<Lecture>>() {

                    public void success(ArrayList<Lecture> data) {
                        System.out.println("win");

                        for (Lecture lecture:data) {
                            System.out.println("Test" + lecture.getDescription());
                            userMenu();
                        }


                    }

                    public void error(int status) {
                        System.out.println("error");
                        System.exit(0);
                    }
                });


                break;

            case 2:

                int userID = user.getId();

           service.findById(userID, new ResponseCallback<ArrayList<Course>>() {
               public void success(ArrayList<Course> data) {

                   for(Course course:data){

                       System.out.println("Displaytext: " + course.getDisplaytext());
                       System.out.println("");
                       System.out.println("Lecture name: " + course.getCode());




                   }
               }

               public void error(int status) {
                   System.out.println(status);
               }
           });

                userMenu();


                break;

            case 3:
                Review review = new Review();
                Scanner inputReader = new Scanner(System.in);

                System.out.println("Type in a Review ID");
                final int reviewID = inputReader.nextInt();
                review.setId(reviewID);

                System.out.println("Type in a lecture ID");
                int lectureID = inputReader.nextInt();

                review.setLectureId(lectureID);

                review.setUserId(user.getId());


                System.out.println("Type in your rating (1-5 points)");
                int rating = inputReader.nextInt();

                review.setRating(rating);

                System.out.println("Type in a comment");
                String reviewComment = inputReader.next();
                review.setComment(reviewComment);

                service.addReview(review, new ResponseCallback<Review>() {
                    public void success(Review data) {
                        System.out.println("Review succesfully added");
                        userMenu();
                    }

                    public void error(int status) {
                        System.out.println(status);
                    }
                });


                break;

            case 4:




            case 6:
                LoginService.clear();
                MainMenuView mainMenuView = new MainMenuView(viewHandler, service);

            default:
                System.out.println("Default");
                userMenu();
                break;
        }
    }






}

