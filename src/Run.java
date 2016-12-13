import controller.ViewHandler;
import view.MainView;
import sdk.service.Service;

import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */

/**
 * Main method to start client.
 */


public class Run {
    public static void main(String[] args) {

        //Creates a service object, so requests to server can be made.
        Service service = new Service();

        //Creates inputreader, so inputs may be insert by user.
        Scanner inputreader = new Scanner(System.in);

        //Creates viewhandler, to handle all views.
        ViewHandler viewHandler = new ViewHandler();

        //Creates mainView, passing service, inputreader and viewhandler as parameters
        MainView mainView = new MainView(service, inputreader, viewHandler);

        //Adds MainView to viewhandler, and runs MainMenu method.
        viewHandler.setMainView(mainView);
        viewHandler.getMainView().MainMenu();
    }
}


