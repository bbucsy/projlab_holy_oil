package hu.holyoil;

import hu.holyoil.commandhandler.Logger;
import hu.holyoil.controller.*;

/**
 * A program futására írt osztály, amelyben a main metódus lakik.
 */
public class Main {

    /**
     * Segéd-tagváltozó, amelynek segítségével be tudjuk állítani, használja-e a randomizálást a program.
     */
    public static Boolean isRandomEnabled;

    /**
     * A program központi indító függvénye.
     * @param args program indításakor megadott argumentumok
     */
    public static void main(String[] args) {
        isRandomEnabled = true;

        // Making sure every controller already existing
        Logger.SetEnabled(false);
        SunController.GetInstance();
        AIController.GetInstance();
        GameController.GetInstance();
        TurnController.GetInstance();
        Logger.SetEnabled(true);

        InputOutputController.GetInstance().ParseCommand(System.in);
    }

}
