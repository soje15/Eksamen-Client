package controller;
import sdk.connection.ResponseCallback;
import sdk.models.*;
import sdk.service.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class UserController {

    private Service service;
    private Scanner inputReader;
    private UserDTO user;

    public UserController(Service service, UserDTO user, Scanner inputReader) {
        this.service = service;
        this.user = user;
        this.inputReader = inputReader;


    }


    public void userMenu() {
        System.out.println("====== UserDTO menu ======");
        System.out.println();
        System.out.println("(1) - Show all lectures");
        System.out.println("(2) - Show attended courses");
        System.out.println("(3) - Add review to course");
        System.out.println("(4) - Show all reviews on a lecture");
        System.out.println("(5) - softdelete your own review");
        System.out.println("(6) - Delete review comment");
        System.out.println("(7) - log out");
        System.out.println();
        try {

            int choice = inputReader.nextInt();

            switch (choice) {
                case 1:

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
                    /*
                    service.getCourses(user.getId(), new ResponseCallback<ArrayList<CourseDTO>>() {

                        public void success(ArrayList<CourseDTO> data) {

                            ArrayList<CourseDTO> courseArrayList = new ArrayList<CourseDTO>();


                            for (CourseDTO course : data) {
                                CourseDTO courses = new CourseDTO();

                                courses.setDisplaytext(course.getDisplaytext());

                                System.out.println(course.getDisplaytext());
                                courseArrayList.add(courses);


                            }

                            for (CourseDTO courseslist : courseArrayList) {


                                service.getAllLectures(courseslist.getDisplaytext(), new ResponseCallback<ArrayList<LectureDTO>>() {
                                    public void success(ArrayList<LectureDTO> data) {

                                        for (LectureDTO lecture : data) {
                                            System.out.println();
                                            System.out.println("Fag: " + lecture.getDescription());
                                            System.out.println("Type: " + lecture.getType());
                                            System.out.println("Start tidspunkt " + lecture.getStartDate());
                                            System.out.println("Slut tidspunkt " + lecture.getEndDate());
                                            System.out.println("Lokation: " +  lecture.getLocation());
                                            System.out.println(lecture.getTeacher());
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
                    */

                    System.out.println();
                    userMenu();
                    break;


                case 2:

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
                    System.out.println();
                    userMenu();


                    break;

                case 3:

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
                    }catch(Exception e) {
                        System.out.println("Something went wrong, when trying to GET lectures");
                    }

                    ReviewDTO review = new ReviewDTO();

                    System.out.println("Type in a lecture ID");
                    int lectureID = inputReader.nextInt();



                    if(lectureIDList.contains(lectureID)) {

                        review.setLectureId(lectureID);



                    } else {
                        System.out.println("Invalid lectureID, please pick one from the list");
                        userMenu();
                    }



                    review.setUserId(user.getId());


                    System.out.println("Type in your rating (1-5 points)");
                    int rating = inputReader.nextInt();

                    if (rating > 5) {
                        System.out.println("Your rating is invalid(max 5)");
                        userMenu();
                    } else if (rating <= 0){
                        System.out.println();
                        System.out.println("Your rating is invalid (atleast 1, up to 5) ");
                        System.out.println();
                        userMenu();

                            }

                    review.setRating(rating);

                    System.out.println("Type in a comment");

                    String reviewComment = inputReader.nextLine();

                    String name = inputReader.nextLine();


                    review.setComment(name);


                    service.addReview(review, new ResponseCallback<String>() {

                        public void success(String data) {
                            System.out.println("ReviewDTO succesfully added");
                            userMenu();
                        }

                        public void error(int status) {
                            System.out.println(status);
                        }
                    });


                    break;

                case 4:
                    service.getAllLecturesByUserID(user.getId(), new ResponseCallback<ArrayList<LectureDTO>>() {
                        public void success(ArrayList<LectureDTO> data) {
                            for (LectureDTO lecture:data) {
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
                    }catch (Exception e) {
                        System.out.println("Could not find reviews");
                    }
                    System.out.println();
                    userMenu();

                   break;

                case 5:

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



                    break;

                case 6:

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

                    userMenu();



                    break;



                case 7:
                    user = null;
                  MainController mainController = new MainController(service, inputReader);
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

