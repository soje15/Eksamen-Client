package view;
import controller.ViewHandler;
import sdk.connection.ResponseCallback;
import sdk.models.*;
import sdk.services.LoginService;
import sdk.services.Service;

import java.io.IOException;
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
                service.findById(user.getId(), new ResponseCallback<ArrayList<Course>>() {

                    public void success(ArrayList<Course> data) {
                        Course courses = new Course();


                        for (Course course : data) {


                            courses.setDisplaytext(course.getDisplaytext());



                        }

                        service.getAll(courses.getDisplaytext(), new ResponseCallback<ArrayList<Lecture>>() {
                            public void success(ArrayList<Lecture> data) {

                                for (Lecture lecture : data) {
                                    System.out.println();
                                    System.out.println("Fag: " + lecture.getDescription());
                                    System.out.println("Type: " + lecture.getType());
                                    System.out.println("Start tidspunkt " + lecture.getStartDate());
                                    System.out.println("Slut tidspunkt " + lecture.getEndDate());
                                    System.out.println();
                                }
                                userMenu();


                            }

                            public void error(int status) {

                            }
                        });

                    }

                    public void error(int status) {

                    }



                });


                /*

                System.out.println("Indtast dit kursus ID, fra listen.");
                System.out.println("BALJO1001U_XJA_E16");
                System.out.println("BBLCO1242U_XA_E16");
                String courseCode = new Scanner(System.in).next();

                //Eks: BALJO1001U_XJA_E16


                service.getAll(courseCode, new ResponseCallback<ArrayList<Lecture>>() {


                    public void success(ArrayList<Lecture> data) {

                            for (Lecture lecture : data) {
                                System.out.println();
                                System.out.println("Fag: " + lecture.getDescription());
                                System.out.println("Type: " + lecture.getType());
                                System.out.println("Start tidspunkt " + lecture.getStartDate());
                                System.out.println("Slut tidspunkt " + lecture.getEndDate());
                                System.out.println();
                            }
                        userMenu();


                    }

                    public void error(int status) {
                        System.out.println("error");
                        System.exit(0);
                    }
                });
                */


                break;

            case 2:

                int userID = user.getId();

                service.findById(userID, new ResponseCallback<ArrayList<Course>>() {
                    public void success(ArrayList<Course> data) {

                        for (Course course : data) {

                            System.out.println("Displaytext: " + course.getDisplaytext());
                            System.out.println("course ID " + course.getId());
                            System.out.println("Lecture name: " + course.getCode());


                        }

                        userMenu();
                    }

                    public void error(int status) {
                        System.out.println(status);
                    }
                });




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

                String reviewComment = inputReader.nextLine();

                String name = inputReader.nextLine();


                review.setComment(name);



                service.addReview(review, new ResponseCallback<String>() {

                    public void success(String data) {
                        System.out.println("Review succesfully added");
                        userMenu();
                    }

                    public void error(int status) {
                        System.out.println(status);
                    }
                });


                break;

            case 4:
                Scanner inputReader2 = new Scanner(System.in);

                System.out.println("Type in the ID of the Review, you wish to view.");
                int ReviewID = inputReader2.nextInt();


                service.getAllReviews(ReviewID, new ResponseCallback<ArrayList<Review>>() {
                    public void success(ArrayList<Review> data) {
                        for (Review reviews : data) {
                            System.out.println();
                            System.out.println("Comment: " + reviews.getComment());
                            System.out.println("Lecture ID: " + reviews.getLectureId());
                            System.out.println("Rating: " + reviews.getRating());
                            System.out.println();

                        }
                        userMenu();
                    }

                    public void error(int status) {

                    }
                });




        case 6:
        LoginService.clear();
            user = null;
        MainMenuView mainMenuView = new MainMenuView(viewHandler, service);

        default:
        System.out.println("Default");
        userMenu();
        break;

        }
    }




}

