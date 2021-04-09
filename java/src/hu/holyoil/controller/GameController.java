package hu.holyoil.controller;

import hu.holyoil.Main;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A játékmenetet kezelő singleton osztály. Implementálja az ISteppable interfacet, amivel a köröket kezeli
 * <p>A PROJEKT EZEN FÁZISÁBAN MÉG NINCS KÉSZ, A TESZTELÉSHEZ NEM SZÜKSÉGES. Ennek megfelelően a dokumentáció is csak felszínes megértést nyújt, amennyi a tesztekhez kellhet.</p>
 */
public class GameController implements ISteppable  {

    /**
     * singleton volta miatt szükséges statikus tagváltozó
     */
    private static GameController gameController;
    /**
     * A játékban szereplő összes élő játékos listája
     */
    private List<Settler> settlers;
    /**
     * A pályán található összes ép aszteroida
     * <p>ép: nem robbant fel</p>
     */
    private List<Asteroid> asteroids;

    /**
     * Lépteti a köröket
     */
    @Override
    public void Step() {
        Logger.Log(this, "Steps" + (Main.isRandomEnabled ? "" : " - switched off!"));

        if (Main.isRandomEnabled) {
            CheckWinCondition();
            CheckLoseCondition();
            CheckGameCondition();
        }

        TurnController.GetInstance().ResetMoves();

        Logger.Return();
    }

    /**
     * Minden kör végén ellenőrzi megnyerték-e a játékot a telepesek
     */
    public void CheckWinCondition()  {
        Logger.Log(this,"Checking win condition");
        // todo
        Logger.Return();
    }

    /**
     * Minden kör végén ellenőrzi elvesztették-e a játékot a telepesek
     */
    public void CheckLoseCondition()  {
        Logger.Log(this,"Checking loose condition");
        // todo
        Logger.Return();
    }

    /**
     * Minden kör végén ellenőrzi nyerhető-e még a játék
     */
    public void CheckGameCondition()  {
        Logger.Log(this,"Checking game condition");
        // todo
        Logger.Return(); }

    /**
     * elindítja a játékot.
     */
    public void StartGame()  {
        Logger.Log(this,"Starting game");
        // todo
        Logger.Return();
    }

    /**
     * Hozzáad egy aszteroidát a pályához
     * @param asteroid hozzáadja az asteroids tagváltozóhoz
     */
    public void AddAsteroid(Asteroid asteroid)  {
        Logger.Log(this,"Adding Asteroid <" +  Logger.GetName(asteroid)+ ">");
        asteroids.add(asteroid);
        Logger.Return();
    }

    /**
     * Töröl egy aszteroidát a pályáról
     * <p>(pl ha felrobban az aszteroida)</p>
     * @param asteroid törli az asteroids tagváltozóból
     */
    public void RemoveAsteroid(Asteroid asteroid)  {
        Logger.Log(this,"Removing asteroid <" +  Logger.GetName(asteroid)+ ">");
        asteroids.remove(asteroid);
        Logger.Return();
    }

    /**
     * Felvesz egy új telepest
     * <p>(a játék kezdetén)</p>
     * @param settler hozzáadja a telepest a settlers tagváltozóhoz
     */
    public void AddSettler(Settler settler)  {
        Logger.Log(this,"Adding settler <" +  Logger.GetName(settler)+ ">");
        settlers.add(settler);
        Logger.Return();
    }

    /**
     * Eltávolít egy telepest a játékból
     * <p>(pl ha a telepes meghal)</p>
     * @param settler törli a settlers tagváltozóból
     */
    public void RemoveSettler(Settler settler)  {
        Logger.Log(this,"Removing settler <" +  Logger.GetName(settler)+ ">");
        settlers.remove(settler);
        Logger.Return();
    }

    /**
     * Singleton osztályra így lehet hivatkozni
     * @return viszaad egy instance-ot
     */
    public static GameController GetInstance() {
        if (gameController == null) {
            gameController = new GameController();
        }

        if (Logger.GetName(gameController) == null) {
            Logger.RegisterObject(gameController, ": GameController");
        }

        return gameController;
    }

    /**
     * Privát konstruktor.
     * Nem lehet kívülről meghívni, nem példányosítható.
     * <p>inicializálja a tárolókat üres listákként</p>
     */
    private GameController() {
        settlers = new ArrayList<>();
        asteroids = new ArrayList<>();
    }

}
