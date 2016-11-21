package controller;

import view.AdminView;
import view.MainMenuView;
import view.UserView;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class ViewHandler {


        private MainMenuView mainMenuView;
        private AdminView adminView;
        private UserView userView;

        public ViewHandler(){

        }

        public Object changeView(Object view){

            return view;

        }



        public void showMainMenu(){
            this.mainMenuView.MainMenu();
        }

        public MainMenuView getMainMenuView() {
            return mainMenuView;
        }

        public AdminView getAdminView() {
            return adminView;
        }

        public UserView getUserView() {
            return userView;
        }

    }



