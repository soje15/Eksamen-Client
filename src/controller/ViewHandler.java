package controller;

import view.TestView;
import view.MainMenuView;
import view.UserView;

/**
 * Created by sorenkolbyejensen on 15/11/2016.
 */
public class ViewHandler {


        private MainMenuView mainMenuView;
        private TestView testView;
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

        public TestView getTestView() {
            return testView;
        }

        public UserView getUserView() {
            return userView;
        }

    }



