package controller;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import sdk.connection.ResponseCallback;
import sdk.models.CourseDTO;
import sdk.models.UserDTO;
import sdk.service.Service;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 10/12/2016.
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



    public void courseParticipants(){

        /**
         * Teacher can only see courses, he is assigned to.
         */
        service.getCourses(user.getId(), new ResponseCallback<ArrayList<CourseDTO>>() {
            public void success(ArrayList<CourseDTO> data) {
                for (CourseDTO courses: data){
                    System.out.println(courses.getId());
                    System.out.println(courses.getCode());
                    System.out.println(courses.getDisplaytext());
                }
            }

            public void error(int status) {
                System.out.println("HTTP error code: " + status);
                viewHandler.getTeacherView().teacherMenu();
            }
        });

        System.out.println("Type in the ID of the course you teach");
        int courseId = inputReader.nextInt();

        service.getCourseParticipants(courseId, new ResponseCallback<Integer>() {

            public void success(Integer data) {
                System.out.println("Number of participants on seleceted course: " + data);
                viewHandler.getTeacherView().teacherMenu();
            }

            public void error(int status) {
                System.out.println("HTTP error code: " + status);
                viewHandler.getTeacherView().teacherMenu();
            }
        });
      
    }

}
