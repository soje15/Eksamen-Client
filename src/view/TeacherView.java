package view;

import controller.TeacherController;
import controller.ViewHandler;
import sdk.models.UserDTO;
import sdk.service.Service;

import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 10/12/2016.
 */
public class TeacherView {

    private Service service;
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private TeacherController teacherController;
    private UserDTO user;

    /**
     *
     * @param service
     * @param user
     * @param inputReader
     * @param viewHandler
     */
    public TeacherView(Service service, UserDTO user, Scanner inputReader, ViewHandler viewHandler) {
        this.service = service;
        this.user = user;
        this.inputReader = inputReader;
        this.viewHandler = viewHandler;


        TeacherController teacherController = new TeacherController(service, user, inputReader, viewHandler);
        this.teacherController = teacherController;
    }


    /**
     *
     *
     */
    public void teacherMenu(){

        System.out.println("====== TEACHER MENU =======");
        System.out.println("");
        System.out.println("(1) - See number of participants on course");
        System.out.println("(2) - log out");
        int choice = inputReader.nextInt();

        switch (choice) {
            case 1:


                teacherController.courseParticipants();

                break;


            case 2:
                user = null;
                viewHandler.getMainView().MainMenu();

                break;

            default:
                System.out.println("Default");

                break;
        }

    }

}


