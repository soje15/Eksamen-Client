package view;
import controller.UserController;
import controller.ViewHandler;
import sdk.models.*;
import sdk.service.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */

/**
 *
 * This is the UserView, where the user can choose between
 * The different functions of the application.
 */
public class UserView {

    private Service service;
    private ViewHandler viewHandler;
    private UserController userController;
    private UserDTO user;

    /**
     *Constructor to initialize parameters.
     * @param service
     * @param user
     * @param viewHandler
     */
    public UserView(Service service, UserDTO user, ViewHandler viewHandler) {
        this.service = service;
        this.user = user;
        this.viewHandler = viewHandler;


        UserController userController = new UserController(service, user, viewHandler);
        this.userController = userController;


    }

    /**
     * User menu - case switch.
     */

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

            Scanner inputReader = new Scanner(System.in);
            int choice = inputReader.nextInt();

            switch (choice) {
                case 1:
                    userController.showAllLectures();


                    break;


                case 2:
                    userController.showAttendedCourses();



                    break;

                case 3:
                    userController.addReviewToLecture();

                    break;

                case 4:
                    userController.showReviewsOnLecture();

                case 5:
                  userController.softDeleteReview(user.getId());


                    break;

                case 6:
                    userController.deleteReviewComment(user.getId());




                    break;



                case 7:
                   user = null;
                    viewHandler.getMainView().mainMenu();

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

