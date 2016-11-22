package view;
import sdk.connection.ResponseCallback;
import sdk.models.*;
import sdk.services.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class UserView {

    private Service service;
    private Scanner inputReader;
    private User user;

    public UserView(Service service, User user) {
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
        System.out.println("(5) - Delete review");
        System.out.println("(6) - Delete review comment");
        System.out.println("(7) - log out");
        System.out.println();
        try {

            int choice = new Scanner(System.in).nextInt();

            switch (choice) {
                case 1:
                    service.findById(user.getId(), new ResponseCallback<ArrayList<Course>>() {

                        public void success(ArrayList<Course> data) {

                            ArrayList<Course> courseArrayList = new ArrayList<Course>();


                            for (Course course : data) {
                                Course courses = new Course();

                                courses.setDisplaytext(course.getDisplaytext());

                                System.out.println(course.getDisplaytext());
                                courseArrayList.add(courses);


                            }

                            for (Course courseslist : courseArrayList) {


                                service.getAll(courseslist.getDisplaytext(), new ResponseCallback<ArrayList<Lecture>>() {
                                    public void success(ArrayList<Lecture> data) {

                                        for (Lecture lecture : data) {
                                            System.out.println();
                                            System.out.println("Fag: " + lecture.getDescription());
                                            System.out.println("Type: " + lecture.getType());
                                            System.out.println("Start tidspunkt " + lecture.getStartDate());
                                            System.out.println("Slut tidspunkt " + lecture.getEndDate());
                                            System.out.println();
                                        }


                                    }


                                    public void error(int status) {
                                        System.out.println(status);

                                    }

                                });
                            }
                        }

                        public void error(int status) {

                        }


                    });
                    System.out.println();
                    userMenu();
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


                        }

                        public void error(int status) {
                            System.out.println(status);
                        }
                    });
                    System.out.println();
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
                    System.out.println("Type in the ID of the Review, you wish to view.");
                   Scanner inputReader3 = new Scanner(System.in);
                    int ReviewID = inputReader3.nextInt();

                    service.getAllReviews(ReviewID, new ResponseCallback<ArrayList<Review>>() {
                        public void success(ArrayList<Review> data) {
                            for (Review reviews : data) {


                                System.out.println();
                                System.out.println("Comment: " + reviews.getComment());
                                System.out.println("Lecture ID: " + reviews.getLectureId());
                                System.out.println("Rating: " + reviews.getRating());
                                System.out.println();

                            }

                        }

                        public void error(int status) {

                        }
                    });
                    System.out.println();
                    userMenu();

                   break;

                case 5:
                    Scanner inputReader2 = new Scanner(System.in);
                    Review deleteReview = new Review();

                    System.out.println("Type in the ID of the review, you wish to delete:");
                    int deleteReviewID = inputReader2.nextInt();

                    deleteReview.setId(deleteReviewID);
                    deleteReview.setUserId(user.getId());

                    service.updateReview(deleteReview, new ResponseCallback<Boolean>() {
                        public void success(Boolean data) {
                            System.out.println(data);
                            System.out.println("Review softdeleted");
                        }

                        public void error(int status) {
                            System.out.println(status);

                        }
                    });



                    break;

                case 6:
                    Scanner inputReader4 = new Scanner(System.in);

                    Review reviewcomment = new Review();

                    System.out.println("Type in the ID of the review");
                    int reviewsID = inputReader4.nextInt();

                    reviewcomment.setUserId(user.getId());
                    reviewcomment.setId(reviewsID);

                     service.deleteReviewComment(reviewcomment, new ResponseCallback<Boolean>() {
                         public void success(Boolean data) {
                             System.out.println("Comment deleted");
                         }

                         public void error(int status) {

                         }
                     });

                    userMenu();



                    break;



                case 7:
                    user = null;
                    MainMenuView mainMenuView = new MainMenuView(service);
                    break;

                default:
                    System.out.println("Default");
                    userMenu();
                    break;

            }
        } catch (InputMismatchException e) {
            System.out.println("indtast venligst et gyldigt tal");
            userMenu();
        }


    }
}

