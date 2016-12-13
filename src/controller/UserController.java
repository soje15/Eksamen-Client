package controller;

import sdk.connection.ResponseCallback;
import sdk.models.*;
import sdk.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */

/**
 * UserController, containing all logic of the user-methods.
 * These include the following:
 *
 * ShowAllLectures
 * showAttendedCourses
 * addReviewToLecture
 * showReviewsOnLecture
 * softDeleteReview
 * deleteReviewComment
 */
public class UserController {

    private Service service;
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private UserDTO user;

    public UserController(Service service, UserDTO user, Scanner inputReader, ViewHandler viewHandler) {
        this.service = service;
        this.user = user;
        this.inputReader = inputReader;
        this.viewHandler = viewHandler;
    }

    public static void main(String[] args) {

    }

    /**
     *Method to show all Lectures belonging to a specific userId.
     */
    public void showAllLectures() {

        //passing user id and implementing Callback interface.
        service.getAllLecturesFromUserID(user.getId(), new ResponseCallback<ArrayList<LectureDTO>>() {

            //On success, print out arraylist.
            public void success(ArrayList<LectureDTO> data) {
                for (LectureDTO lecture : data) {
                    System.out.println();
                    System.out.println("Fag: " + lecture.getDescription());
                    System.out.println("Type: " + lecture.getType());
                    System.out.println("Start tidspunkt " + lecture.getStartDate());
                    System.out.println("Slut tidspunkt " + lecture.getEndDate());
                    System.out.println("Lokation: " + lecture.getLocation());
                    System.out.println();
                }
            }

            //Handling error, returning to usermenu.
            public void error(int status) {
                System.out.println("HTTP Error Status: " + status);
                viewHandler.getUserView().userMenu();
            }
        });

        //return to usermenu
        System.out.println();
        viewHandler.getUserView().userMenu();

    }

    /**
     *Method to show which courses a user is attending. From UserID
     */
    public void showAttendedCourses() {

        //Calling service method, passing user id and implemetning callback interface.
        service.getCourses(user.getId(), new ResponseCallback<ArrayList<CourseDTO>>() {

            //Printout out recieved arraylist with courses.
            public void success(ArrayList<CourseDTO> data) {

                for (CourseDTO courses : data) {
                    System.out.println();
                    System.out.println("Displaytext: " + courses.getDisplaytext());
                    System.out.println("course ID " + courses.getId());
                    System.out.println("LectureDTO name: " + courses.getCode());
                    System.out.println();


                }


            }

            //Handling error, returning to usermenu.
            public void error(int status) {
                System.out.println("HTTP Error Status: " + status);
                System.out.println();
                viewHandler.getUserView().userMenu();
            }
        });


        viewHandler.getUserView().userMenu();

        System.out.println();

    }

    /**
     *Method to addreview to a specific lecture.
     */
    public void addReviewToLecture() {

        //Creating list, to avoid user adding review to lecture he/she is not attending.
        final List<Integer> lectureIDList = new ArrayList<Integer>();


        try {
            service.getAllLecturesFromUserID(user.getId(), new ResponseCallback<ArrayList<LectureDTO>>() {
                public void success(ArrayList<LectureDTO> data) {

                    //Printing out recieved arraylist of lectures, so user can see
                    //Which lectures he can review.
                    for (LectureDTO lectures : data) {
                        System.out.println();
                        System.out.println("LectureDTO: " + lectures.getDescription());
                        System.out.println("Date: " + lectures.getStartDate());
                        System.out.println("id: " + lectures.getId());
                        System.out.println();

                        //Adding lectureId to list, so the "contains" method may be used.
                        lectureIDList.add(lectures.getId());
                    }


                }

                //Handling error, returning to usermenu.
                public void error(int status) {
                    System.out.println("Something went wrong, when trying to GET lectures");
                    viewHandler.getUserView().userMenu();

                }
            });

            //Catching exception
        } catch (Exception e) {
            System.out.println("Something went wrong, when trying to GET lectures");
            viewHandler.getUserView().userMenu();
        }

        /*
        Preparing to parse review to server.
         */
        ReviewDTO review = new ReviewDTO();

        System.out.println("Type in a lecture ID");
        int lectureID = inputReader.nextInt();


        //Checking if user typed in valid lectureID.
        if (lectureIDList.contains(lectureID)) {

            review.setLectureId(lectureID);


        } else {
            System.out.println("Invalid lectureID, please pick one from the list");
            viewHandler.getUserView().userMenu();
        }


        review.setUserId(user.getId());

        System.out.println("Type in your rating (1-5 points)");

        int rating = inputReader.nextInt();


        /**
         * Making sure the rating is within 1-5 stars.
         */
        if (rating > 5) {
            System.out.println("Your rating is invalid(max 5)");
            viewHandler.getUserView().userMenu();


        } else if (rating <= 0) {
            System.out.println();
            System.out.println("Your rating is invalid (atleast 1, up to 5) ");
            System.out.println();
            viewHandler.getUserView().userMenu();
        }

        /*
        Further review variables are set.
         */
        review.setRating(rating);

        System.out.println("Type in a comment");

        String comment = inputReader.next();

        review.setComment(comment);


        /*
        Review is attempted to added, by parsing review object.
         */
        service.addReview(review, new ResponseCallback<String>() {

            //If string is recived, callback was sucessful.
            public void success(String data) {
                System.out.println("ReviewDTO succesfully added");
            }

            //Handling error, returning to usermenu.
            public void error(int status) {
                System.out.println(status);
                viewHandler.getUserView().userMenu();
            }
        });

        viewHandler.getUserView().userMenu();
    }

    /**
     *Method to show reviews on a specific lecture.
     */
    public void showReviewsOnLecture() {

        //Calling service method to get lectures from a userid, implemeting callbakc interface.
        //Used so user can decide which lecture he wants to view reviews for.
        service.getAllLecturesFromUserID(user.getId(), new ResponseCallback<ArrayList<LectureDTO>>() {

            //Print out array of lectures
            public void success(ArrayList<LectureDTO> data) {
                for (LectureDTO lecture : data) {
                    System.out.println();
                    System.out.println("Lecture Description: " + lecture.getDescription());
                    System.out.println("Lecture Date: " + lecture.getEndDate());
                    System.out.println("Lecture ID: " + lecture.getId());
                }
            }

            public void error(int status) {
                System.out.println("HTTP Error Status: " + status);
                viewHandler.getUserView().userMenu();

            }
        });

        /*
            Preparing to get the review from the server, from a lectureId.
         */
        System.out.println();
        System.out.println("Type in the ID of the lecture, you wish to view reviews for.");

        int lectureId = inputReader.nextInt();

        try {

            //Calling getallreviews method, passing lectureid and implementing callback interface.
            service.getAllReviews(lectureId, new ResponseCallback<ArrayList<ReviewDTO>>() {

                //print out array of reviews.
                public void success(ArrayList<ReviewDTO> data) {
                    for (ReviewDTO reviews : data) {
                        System.out.println();
                        System.out.println("Comment: " + reviews.getComment());
                        System.out.println("LectureDTO ID: " + reviews.getLectureId());
                        System.out.println("Rating: " + reviews.getRating());
                        System.out.println();

                    }

                }

                //Handling error, returning to usermenu.
                public void error(int status) {
                    System.out.println("HTTP Error Status: " + status);
                    viewHandler.getUserView().userMenu();

                }
            });

            //catching exception
        } catch (Exception e) {
            System.out.println("Could not find reviews");
        }
        System.out.println();
        viewHandler.getUserView().userMenu();

    }

    /**
     *Method to softdelete a users OWN review.
     *
     */

    //User id, passed from userview.
    public void softDeleteReview(int userId) {

        //Getting reviews from userId, so user can see which reviews he/she can delete.
        service.getReviews(userId, new ResponseCallback<ArrayList<ReviewDTO>>() {
            public void success(ArrayList<ReviewDTO> data) {

                //Printing out array of reviews
                for (ReviewDTO reviews: data) {
                    System.out.println();
                    System.out.println("Lecture ID : " + reviews.getLectureId());
                    System.out.println("Comment: " + reviews.getComment());
                    System.out.println("Rating: " + reviews.getRating());
                    System.out.println("Review ID: " + reviews.getId());
                    System.out.println();


                }
            }

            //Handling error, returning to usermenu.
            public void error(int status) {
                System.out.println("HTTP Error Status: " + status);

            }
        });


        /*
        Preparing review to be softdeleted.
         */
        ReviewDTO deleteReview = new ReviewDTO();

        System.out.println("Type in the ID of the review, you wish to delete:");
        int deleteReviewID = inputReader.nextInt();


        deleteReview.setId(deleteReviewID);
        deleteReview.setUserId(user.getId());

        /*
        Sending review object to server, so that it may be softdeleted.
         */
        service.updateReview(deleteReview, new ResponseCallback<Boolean>() {
            public void success(Boolean data) {
                System.out.println(data);
                System.out.println("ReviewDTO softdeleted");
            }

            public void error(int status) {
                System.out.println("HTTP Error Status: " + status);
                viewHandler.getUserView().userMenu();

            }
        });
        viewHandler.getUserView().userMenu();

    }

    /**
     *Method to delete Review Comment from userId
     *
     */

    public void deleteReviewComment(int userId) {

        //GEtreviews from userId and implementing callback handler
        service.getReviews(userId, new ResponseCallback<ArrayList<ReviewDTO>>() {
            public void success(ArrayList<ReviewDTO> data) {

                //Print out reviews
                for (ReviewDTO reviews: data) {
                    System.out.println();
                    System.out.println("Lecture ID : " + reviews.getLectureId());
                    System.out.println("Comment: " + reviews.getComment());
                    System.out.println("Review ID: " + reviews.getId());
                    System.out.println();
                }
            }

            public void error(int status) {
                System.out.println(status + "Could not get reviews");

            }
        });


        /**
         *
         * Preparing review to have its review deleted.
         */
        ReviewDTO reviewcomment = new ReviewDTO();

        System.out.println("Type in the ID of the review");
        int reviewsID = inputReader.nextInt();

        reviewcomment.setUserId(user.getId());
        reviewcomment.setId(reviewsID);

        /*
        Service method, to delete reviews comment.
         */
        service.deleteReviewComment(reviewcomment, new ResponseCallback<Boolean>() {
            public void success(Boolean data) {
                System.out.println("Comment deleted");
            }

            public void error(int status) {
                System.out.println("HTTP Error Status: " + status);
                viewHandler.getUserView().userMenu();

            }
        });
        viewHandler.getUserView().userMenu();

    }


}

