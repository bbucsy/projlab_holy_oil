package hu.holyoil;

import hu.holyoil.controller.Logger;
import hu.holyoil.controller.*;

/**
 * A program futására írt osztály, amelyben a main metódus lakik.
 */
public class Main {
    /**
     * A program központi indító függvénye.
     * @param args program indításakor megadott argumentumok
     */
    public static void main(String[] args) {
        // Making sure every controller already existing
        SunController.GetInstance();
        AIController.GetInstance();
        TurnController.GetInstance();

        GameController.GetInstance().StartApp();
    }

}
