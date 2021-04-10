package hu.holyoil.controller;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.*;
import hu.holyoil.skeleton.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
     * A játék jelenlegi állása: running, lost vagy won
     */
    private GameState gameState = GameState.RUNNING;

    /**
     * Visszaadja a kiírás érdekében a jelenlegi állást
     * @return játékállás
     */
    public GameState GetGameState() {
        return gameState;
    }

    /**
     * Lépteti a köröket
     */
    @Override
    public void Step() {
        Logger.Log(this, "Steps");

        CheckWinCondition();
        CheckLoseCondition();
        CheckGameCondition();

        TurnController.GetInstance().ResetMoves();

        Logger.Return();
    }

    /**
     * Belső osztály a nyersanyag leszámolások megoldására
     */
    class CounterVector {
        long coalCount;
        long ironCount;
        long uraniumCount;
        long waterCount;

        /**
         * Összeszámolja a kollekcióban található elemeket külön-külön
         * @param collection a kollekció, ami a nyersanyagokat tartalmazza
         */
        private void CountResourcesSeparately(List<AbstractBaseResource> collection) {
            coalCount = collection
                    .stream()
                    .filter(abstractBaseResource -> abstractBaseResource.IsSameType(new Coal()))
                    .count();
            ironCount = collection
                    .stream()
                    .filter(abstractBaseResource -> abstractBaseResource.IsSameType(new Iron()))
                    .count();
            uraniumCount = collection
                    .stream()
                    .filter(abstractBaseResource -> abstractBaseResource.IsSameType(new Uranium()))
                    .count();
            waterCount = collection
                    .stream()
                    .filter(abstractBaseResource -> abstractBaseResource.IsSameType(new Water()))
                    .count();
        }

        /**
         * Kiszámolja és egy tömbben visszaadja az egyes nyersanyagtípusokból a játékban lévő mennyiséget
         */
        private void CountInGameResources() {
            List<AbstractBaseResource> collection = asteroids
                    .stream()
                    .filter(asteroid -> asteroid.GetResource() != null)
                    .map(Asteroid::GetResource)
                    .collect(Collectors.toList());

            settlers
                    .stream()
                    .map(settler -> settler.GetStorage().GetStoredMaterials())
                    .forEach(collection::addAll);

            CountResourcesSeparately(collection);
        }

        private void CountAsteroidsResources(Asteroid asteroid) {
            List<AbstractBaseResource> collection = new LinkedList<>();
            settlers
                    .stream()
                    .filter(settler -> settler.GetOnAsteroid() == asteroid)
                    .map(settler -> settler.GetStorage().GetStoredMaterials())
                    .forEach(collection::addAll);

            CountResourcesSeparately(collection);
        }

        /**
         * Visszaadja, képesek-e a telepesek még nyerni, van-e annyi anyag a terepen, amivel megnyerhető a játék
         * @return megnyerhetősége a játéknak
         */
        public boolean CanWin() {
            return coalCount >= 3 && ironCount >= 3 && uraniumCount >= 3 && waterCount >= 3;
        }
    }

    /**
     * Minden kör végén ellenőrzi megnyerték-e a játékot a telepesek
     */
    public void CheckWinCondition()  {
        Logger.Log(this,"Checking win condition");

        if (gameState == GameState.RUNNING) {
            for (Asteroid asteroid : asteroids) {
                CounterVector counterVector = new CounterVector();
                counterVector.CountAsteroidsResources(asteroid);
                if (counterVector.CanWin()) {
                    gameState = GameState.WON_GAME;
                    break;
                }
            }
        }

        Logger.Return();
    }

    /**
     * Minden kör végén ellenőrzi elvesztették-e a játékot a telepesek
     */
    public void CheckLoseCondition()  {
        Logger.Log(this,"Checking loose condition");

        if (gameState == GameState.RUNNING) {
            if (settlers.size() == 0) {
                gameState = GameState.LOST_GAME;
            }
        }

        Logger.Return();
    }

    /**
     * Minden kör végén ellenőrzi nyerhető-e még a játék
     */
    public void CheckGameCondition()  {
        Logger.Log(this,"Checking game condition");

        if (gameState == GameState.RUNNING) {
            CounterVector counterVector = new CounterVector();
            counterVector.CountInGameResources();
            if (!counterVector.CanWin()) {
                gameState = GameState.LOST_GAME;
            }
        }

        Logger.Return();
    }

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
