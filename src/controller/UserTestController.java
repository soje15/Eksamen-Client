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
public class UserTestController {

    private Service service;
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private UserDTO user;

    public UserTestController(Service service, UserDTO user, Scanner inputReader, ViewHandler viewHandler) {
        this.service = service;
        this.user = user;
        this.inputReader = inputReader;
        this.viewHandler = viewHandler;
    }

    /**
     *
     */
    public void showAllLectures() {
        service.getAllLecturesByUserID(user.getId(), new ResponseCallback<ArrayList<LectureDTO>>() {
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

            public void error(int status) {

            }
        });

        System.out.println();
        viewHandler.getUserView().userMenu();

    }

    /**
     *
     */
    public void showAttendedCourses() {


        int userID = user.getId();

        service.getCourses(userID, new ResponseCallback<ArrayList<CourseDTO>>() {
            public void success(ArrayList<CourseDTO> data) {

                for (CourseDTO course : data) {
                    System.out.println();
                    System.out.println("Displaytext: " + course.getDisplaytext());
                    System.out.println("course ID " + course.getId());
                    System.out.println("LectureDTO name: " + course.getCode());
                    System.out.println();


                }


            }

            public void error(int status) {
                System.out.println(status);
            }
        });
        viewHandler.getUserView().userMenu();

        System.out.println();

    }

    /**
     *
     */
    public void addReviewToLecture() {
        final List<Integer> lectureIDList = new ArrayList<Integer>();


        try {
            service.getAllLecturesByUserID(user.getId(), new ResponseCallback<ArrayList<LectureDTO>>() {
                public void success(ArrayList<LectureDTO> data) {

                    for (LectureDTO lectures : data) {
                        System.out.println();
                        System.out.println("LectureDTO: " + lectures.getDescription());
                        System.out.println("Date: " + lectures.getStartDate());
                        System.out.println("id: " + lectures.getId());
                        System.out.println();
                        lectureIDList.add(lectures.getId());
                    }


                }

                public void error(int status) {
                    System.out.println("Something went wrong, when trying to GET lectures");

                }
            });
        } catch (Exception e) {
            System.out.println("Something went wrong, when trying to GET lectures");
        }

        ReviewDTO review = new ReviewDTO();

        System.out.println("Type in a lecture ID");
        int lectureID = inputReader.nextInt();


        if (lectureIDList.contains(lectureID)) {

            review.setLectureId(lectureID);


        } else {
            System.out.println("Invalid lectureID, please pick one from the list");
            viewHandler.getUserView().userMenu();


        }


        review.setUserId(user.getId());

        System.out.println("Type in your rating (1-5 points)");

        int rating = inputReader.nextInt();





        if (rating > 5) {
            System.out.println("Your rating is invalid(max 5)");
            viewHandler.getUserView().userMenu();


        } else if (rating <= 0) {
            System.out.println();
            System.out.println("Your rating is invalid (atleast 1, up to 5) ");
            System.out.println();
            viewHandler.getUserView().userMenu();


        }

        review.setRating(rating);


        System.out.println("Type in a comment");

        String reviewComment = inputReader.nextLine();

        String name = inputReader.nextLine();


        review.setComment(name);


        service.addReview(review, new ResponseCallback<String>() {

            public void success(String data) {
                System.out.println("ReviewDTO succesfully added");

            }

            public void error(int status) {
                System.out.println(status);
                viewHandler.getUserView().userMenu();
            }
        });

        viewHandler.getUserView().userMenu();
    }

    /**
     *
     */

    public void showReviewsOnLecture() {

        service.getAllLecturesByUserID(user.getId(), new ResponseCallback<ArrayList<LectureDTO>>() {
            public void success(ArrayList<LectureDTO> data) {
                for (LectureDTO lecture : data) {
                    System.out.println("LectureDTO ID: " + lecture.getId());
                }
            }

            public void error(int status) {

            }
        });

        System.out.println();
        System.out.println("Type in the ID of the LectureDTO, you wish to view reviews for.");

        int ReviewID = inputReader.nextInt();

        try {

            service.getAllReviews(ReviewID, new ResponseCallback<ArrayList<ReviewDTO>>() {
                public void success(ArrayList<ReviewDTO> data) {
                    for (ReviewDTO reviews : data) {


                        System.out.println();
                        System.out.println("Comment: " + reviews.getComment());
                        System.out.println("LectureDTO ID: " + reviews.getLectureId());
                        System.out.println("Rating: " + reviews.getRating());
                        System.out.println();

                    }

                }

                public void error(int status) {

                }
            });
        } catch (Exception e) {
            System.out.println("Could not find reviews");
        }
        System.out.println();
        viewHandler.getUserView().userMenu();

    }

    /**
     *
     *
     */


    public void softDeleteReview() {

        ReviewDTO deleteReview = new ReviewDTO();

        System.out.println("Type in the ID of the review, you wish to delete:");
        int deleteReviewID = inputReader.nextInt();

        deleteReview.setId(deleteReviewID);
        deleteReview.setUserId(user.getId());

        service.updateReview(deleteReview, new ResponseCallback<Boolean>() {
            public void success(Boolean data) {
                System.out.println(data);
                System.out.println("ReviewDTO softdeleted");
            }

            public void error(int status) {
                System.out.println(status);

            }
        });
        viewHandler.getUserView().userMenu();

    }

    /**
     *
     *
     */


    public void deleteReviewComment() {

        ReviewDTO reviewcomment = new ReviewDTO();

        System.out.println("Type in the ID of the review");
        int reviewsID = inputReader.nextInt();

        reviewcomment.setUserId(user.getId());
        reviewcomment.setId(reviewsID);

        service.deleteReviewComment(reviewcomment, new ResponseCallback<Boolean>() {
            public void success(Boolean data) {
                System.out.println("Comment deleted");
            }

            public void error(int status) {


            }
        });
        viewHandler.getUserView().userMenu();

    }


}

