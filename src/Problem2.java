import configloader.ConfigLoader;
import sdk.services.Service;
import view.MainMenuView;

/**
 * Created by sorenkolbyejensen on 16/11/2016.
 * Klienten
 DONE K01 :  Klient skal kunne logge ind og ud
 DONE  K02  : Klient skal kunne skrive en kommentar til en lektioner
 DONE K03  : Klient skal kunne se andres kommentarer og bedømmelser
 DONE K04  : Klient skal kunne bedømme lektioner (Rating og evt. kommentere)
 DONE K05  : Klient skal kunne modtage lektioner fra serveren
 UMULIGT MED NUVÆRENDE ENDPOINTS & DATABASE : Klient skal kunne se samlet deltagelse af lektion
 DONE K07 :  Klient skal kunne slette egen kommentar
 DONE Klient (admin-bruger) skal kunne slette kommentarer
 DONE K09 :  Klient skal stille et UI til rådighed for brugeren
 */


public class Problem2 {
    public static void main(String[] args) {
        ConfigLoader.parseConfig();
        Service service = new Service();

        MainMenuView mainMenuView = new MainMenuView(service);
        mainMenuView.MainMenu();

    }
}
