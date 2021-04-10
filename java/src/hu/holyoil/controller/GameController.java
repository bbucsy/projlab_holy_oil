package hu.holyoil.controller;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;
import hu.holyoil.repository.ResourceBaseRepository;
import hu.holyoil.repository.SettlerRepository;
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
    static class CounterVector {
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
                    .filter(abstractBaseResource -> abstractBaseResource.IsSameType(new Coal("EXAMPLE1")))
                    .count();
            ironCount = collection
                    .stream()
                    .filter(abstractBaseResource -> abstractBaseResource.IsSameType(new Iron("EXAMPLE2")))
                    .count();
            uraniumCount = collection
                    .stream()
                    .filter(abstractBaseResource -> abstractBaseResource.IsSameType(new Uranium("EXAMPLE3")))
                    .count();
            waterCount = collection
                    .stream()
                    .filter(abstractBaseResource -> abstractBaseResource.IsSameType(new Water("EXAMPLE4")))
                    .count();
            ResourceBaseRepository.GetInstance().Remove("EXAMPLE1");
            ResourceBaseRepository.GetInstance().Remove("EXAMPLE2");
            ResourceBaseRepository.GetInstance().Remove("EXAMPLE3");
            ResourceBaseRepository.GetInstance().Remove("EXAMPLE4");
        }

        /**
         * Kiszámolja és beállítja az egyes nyersanyagtípusokból a játékban lévő mennyiséget
         */
        private void CountInGameResources() {
            CountResourcesSeparately(ResourceBaseRepository.GetInstance().GetAll());
        }

        /**
         * Kiszámolja és beállítja az egyes nyersanyagtípusokból az aszteroidán lévő mennyiséget
         */
        private void CountAsteroidsResources(Asteroid asteroid) {
            List<AbstractBaseResource> collection = new LinkedList<>();
            SettlerRepository.GetInstance().GetAll()
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
            for (Asteroid asteroid : AsteroidRepository.GetInstance().GetAll()) {
                CounterVector counterVector = new CounterVector();
                counterVector.CountAsteroidsResources(asteroid);
                if (counterVector.CanWin()) {
                    Logger.Log(this,"Just won game!");
                    gameState = GameState.WON_GAME;
                    Logger.Return();
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
        Logger.Log(this,"Checking lose condition");

        if (gameState == GameState.RUNNING) {
            if (SettlerRepository.GetInstance().GetAll().size() == 0) {
                Logger.Log(this,"Just lost game!");
                gameState = GameState.LOST_GAME;
                Logger.Return();
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
                Logger.Log(this,"Just lost game!");
                gameState = GameState.LOST_GAME;
                Logger.Return();
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
    private GameController() { }

}
