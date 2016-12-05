package controller;

import view.AdminView;
import view.UserView;
import view.MainView;

/**
 * Created by sorenkolbyejensen on 30/11/2016.
 */
public class ViewHandler {

    private MainView mainView;
    private UserView userView;
    private AdminView adminView;


    /**
     * Metoder til at håndtere diverse views. (Get & Set)
     * @return
     */


    public MainView getMainView() {
        return mainView;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public UserView getUserView() {
        return userView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }

}
