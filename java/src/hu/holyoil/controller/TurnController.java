package hu.holyoil.controller;

import java.util.HashMap;

public class TurnController {

    private static TurnController turnController;

    /**
     * Hány lépést tehet meg egy telepes / robot / ufo egy körben.
     * */
    private final int NUM_OF_ACTIONS_PER_TURN = 1;

    /**
     * Eltárolja minden objektumról, hogy hány lépést tett már meg.
     * */
    private HashMap<Object, Integer> movesMade;

    /**
     * Visszaadja, hogy egy adott elem léphet-e még.
     * @param object A vizsgálandó objektum
     * @return Léphet-e az adott objektum.
     * */
    public boolean HasActionsLeft(Object object) {
        return movesMade.get(object) < NUM_OF_ACTIONS_PER_TURN;
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
        movesMade.put(object, NUM_OF_ACTIONS_PER_TURN);
    }

    /**
     * Kitöröl egy objektumot a regisztrált objektumok közül.
     * @param object A törlendő objektum
     * */
    public void RemoveEntityWithAction(Object object) {
        movesMade.remove(object);
    }

    public static TurnController GetInstance() {

        if (turnController == null) {
            turnController = new TurnController();
        }

        return turnController;

    }

    TurnController() {
        movesMade = new HashMap<>();
    }

}
