package hu.holyoil;

import hu.holyoil.commandhandler.Logger;
import hu.holyoil.controller.*;
import hu.holyoil.view.IViewComponent;
import hu.holyoil.view.frames.MenuFrame;

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
        TurnController.GetInstance();
        //Logger.SetEnabled(true);

        GameController.GetInstance().StartApp();
    }

}
