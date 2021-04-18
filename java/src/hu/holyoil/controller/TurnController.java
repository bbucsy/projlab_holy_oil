package hu.holyoil.controller;

import hu.holyoil.commandhandler.Logger;

import java.util.HashMap;

/**
 * A köröket irányító singleton kontroller osztály.
 */
public class TurnController {
    /**
     * Singleton osztály statikus tagváltozója amin keresztül el lehet érni.
     */
    private static TurnController turnController;

    /**
     * Hány lépést tehet meg egy telepes / robot / ufo egy körben.
     * */
    public final int NUM_OF_ACTIONS_PER_TURN = 1;

    /**
     * Eltárolja minden objektumról, hogy hány lépést tett már meg.
     * */
    private HashMap<Object, Integer> movesMade;

    /**
     * Visszaadja, hogy egy adott elem lelépte-e már a lépéseit.
     * @param object A vizsgálandó objektum
     * @return Lelépte-e az összes lépését ez az egység.
     * */
    public boolean HasNoActionsLeft(Object object) {
        return movesMade.get(object) >= NUM_OF_ACTIONS_PER_TURN;
    }

    /**
     * Minden lépésre képes objektum esetén visszaállítja a lépett lépések számát.
     * */
    public void ResetMoves() {
        movesMade.replaceAll(
                (obj, i) -> 0
        );
    }

    /**
     * Eltárolja, hogy az adott objektum lépett egyet.
     * @param object A lépett objektum
     * */
    public void ReactToActionMade(Object object) {
        movesMade.put(object, movesMade.get(object) + 1);
    }

    /**
     * Beregisztrál egy egy objektumot, amely tud lépni.
     * @param object A beregisztrálandó objektum
     * */
    public void RegisterEntityWithAction(Object object) {
        movesMade.put(object, 0);
    }

    /**
     * Kitöröl egy objektumot a regisztrált objektumok közül.
     * @param object A törlendő objektum
     * */
    public void RemoveEntityWithAction(Object object) {
        movesMade.remove(object);
    }

    /**
     * Singleton osztályra lehet vele hivatkozni
     * @return visszaad egy instance-et
     */
    public static TurnController GetInstance() {

        if (turnController == null) {
            turnController = new TurnController();
        }

        if (Logger.GetName(turnController) == null) {
            Logger.RegisterObject(turnController, ": TurnController");
        }

        return turnController;

    }

    /**
     * Privát konstruktor.
     * Nem lehet kívülről meghívni, nem lehet példányosítani.
     */
    TurnController() {
        movesMade = new HashMap<>();
    }

}
