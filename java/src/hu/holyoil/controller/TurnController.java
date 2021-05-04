package hu.holyoil.controller;

import hu.holyoil.commandhandler.Logger;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.repository.SettlerRepository;
import hu.holyoil.view.frames.GameFrame;

import javax.swing.*;
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
     * Hány játékos lehet maximum egy játékban
     */
    public final int NUM_OF_PLAYERS_MAX = 4;

    /**
     * Tárolja, melyik telepes van éppen soron
     */
    private Settler steppingSettler;

    /**
     * Tárolja a játékmenet ablakát, amelyben a játék folyik
     */
    private GameFrame gameFrame;

    /**
     * Eltárolja minden objektumról, hogy hány lépést tett már meg.
     * */
    private HashMap<Object, Integer> movesMade;

    public GameFrame GetGameFrame() {
        return gameFrame;
    }

    public void SetGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    /**
     * Elindítja a lépéskezelő alrendszerét a lépés kontrollernek, azaz léptetésbe kezd a játék.
     * Alapvetően a GameController StartGame metódusa által hívódik.
     * Beállítja a jelenleg soron lévő telepest a repó első tagjára.
     */
    public void StartTurnSystem() {
        steppingSettler = SettlerRepository.GetInstance().GetAll().get(0);
    }

    /**
     * Visszaadja a jelenleg soron lévő játékos telepesét
     * @return soron lévő telepes
     */
    public Settler GetSteppingSettler() {
        return steppingSettler;
    }

    private Settler GetNextSettler() {
        for (Settler settler : SettlerRepository.GetInstance().GetAll()) {
            if (!HasNoActionsLeft(settler))
                return settler;
        }
        return null;
    }

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

    public void ReactToActionMade(Settler object) {
        movesMade.put(object, movesMade.get(object) + 1);
        Settler temp = GetNextSettler();
        if (temp == null) {
            AIController.GetInstance().Step();
            SunController.GetInstance().Step();
            GameController.GetInstance().Step();
            StartTurnSystem();
        }
        else {
            steppingSettler = temp;
        }
        gameFrame.UpdateComponent();

        switch (GameController.GetInstance().GetGameState()) {
            case WON_GAME:
                JOptionPane.showMessageDialog(gameFrame, "You've won the game!", "Holy Oil Game", JOptionPane.INFORMATION_MESSAGE);
                gameFrame.setVisible(false);
                GameController.GetInstance().StartApp();
                break;
            case LOST_GAME:
                JOptionPane.showMessageDialog(gameFrame, "You've lost the game!", "Holy Oil Game", JOptionPane.ERROR_MESSAGE);
                gameFrame.setVisible(false);
                GameController.GetInstance().StartApp();
                break;
        }
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
