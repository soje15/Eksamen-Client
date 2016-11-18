import configloader.ConfigLoader;
import controller.ViewHandler;
import sdk.services.Service;
import view.MainMenuView;

/**
 * Created by sorenkolbyejensen on 16/11/2016.
 */
public class Problem2 {
    public static void main(String[] args) {
        ConfigLoader.parseConfig();
        Service service = new Service();
        ViewHandler viewHandler = new ViewHandler();

        MainMenuView mainMenuView = new MainMenuView(viewHandler, service);
        mainMenuView.MainMenu();

    }
}
