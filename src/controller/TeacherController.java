package controller;


import sdk.connection.ResponseCallback;
import sdk.models.UserDTO;
import sdk.service.Service;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 10/12/2016.
 */

/**
 * This class includes the function(s) available for teachers.
 *
 * In this application, the teacher must be able to see how many students are assigned to a course.
 */
public class TeacherController {
    private Service service;
    private Scanner inputReader;
    private ViewHandler viewHandler;
    private UserDTO user;


    public TeacherController(Service service, UserDTO user, Scanner inputReader, ViewHandler viewHandler) {
        this.service = service;
        this.inputReader = inputReader;
        this.viewHandler = viewHandler;
        this.user = user;
    }

    /**
     * Returns number of students on a specific course ID.
     */
    public void courseParticipants() {

        try {

            System.out.println("Type in the ID of the course you teach");
            int courseId = inputReader.nextInt();

            //Get courseparticipants from courseid
            service.getCourseParticipants(courseId, new ResponseCallback<Integer>() {

                //Return integer on sucess, print it.
                public void success(Integer data) {
                    System.out.println();
                    System.out.println("Number of participants on seleceted course: " + data);
                    System.out.println();
                    viewHandler.getTeacherView().teacherMenu();
                }

                //Handling error, returning to teachermenu.
                public void error(int status) {
                    System.out.println("HTTP error code: " + status);
                    viewHandler.getTeacherView().teacherMenu();
                }
            });


        } catch (Exception e) {
            viewHandler.getTeacherView().teacherMenu();
        }

    }
}
