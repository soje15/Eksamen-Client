package view;
import controller.UserTestController;
import controller.ViewHandler;
import sdk.models.*;
import sdk.service.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class UserView {

    private Service service;
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private UserTestController userTestController;
    private UserDTO user;

    public UserView(Service service, UserDTO user, Scanner inputReader, ViewHandler viewHandler) {
        this.service = service;
        this.user = user;
        this.inputReader = inputReader;
        this.viewHandler = viewHandler;


        UserTestController userTestController = new UserTestController(service, user, inputReader, viewHandler);
        this.userTestController = userTestController;


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
                    userTestController.showAllLectures();


                    break;


                case 2:
                    userTestController.showAttendedCourses();



                    break;

                case 3:
                    userTestController.addReviewToLecture();

                    break;

                case 4:
                    userTestController.showReviewsOnLecture();

                case 5:
                  userTestController.softDeleteReview();


                    break;

                case 6:
                    userTestController.deleteReviewComment();




                    break;



                case 7:
                   user = null;
                    viewHandler.getMainView().MainMenu();

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

