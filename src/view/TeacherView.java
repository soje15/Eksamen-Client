package view;

import com.sun.tools.doclets.internal.toolkit.util.DocFinder;
import controller.TeacherController;
import controller.ViewHandler;
import sdk.models.UserDTO;
import sdk.service.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 10/12/2016.
 */
public class TeacherView {

    private Service service;
    private ViewHandler viewHandler;
    private TeacherController teacherController;
    private UserDTO user;

    /**
     *
     * @param service
     * @param user
     * @param viewHandler
     */
    public TeacherView(Service service, UserDTO user, ViewHandler viewHandler) {
        this.service = service;
        this.user = user;
        this.viewHandler = viewHandler;


        TeacherController teacherController = new TeacherController(service, user, viewHandler);
        this.teacherController = teacherController;
    }


    /**
     *
     *
     */
    public void teacherMenu(){
        Scanner inputReader = new Scanner(System.in);

        System.out.println("====== TEACHER MENU =======");
        System.out.println("");
        System.out.println("(1) - See number of participants on course");
        System.out.println("(2) - log out");

        try{
        int choice = inputReader.nextInt();

        switch (choice) {
            case 1:


                teacherController.courseParticipants();

                break;


            case 2:
                user = null;
                viewHandler.getMainView().mainMenu();

                break;

            default:
                System.out.println("Default");

                break;
        }

    }catch(InputMismatchException e){
        System.out.println("Type in valid input");
        teacherMenu();
    }}

}


