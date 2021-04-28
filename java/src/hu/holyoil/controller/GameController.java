package hu.holyoil.controller;

import hu.holyoil.Main;
import hu.holyoil.commandhandler.Logger;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.crewmate.Ufo;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;
import hu.holyoil.repository.ResourceBaseRepository;
import hu.holyoil.repository.SettlerRepository;
import hu.holyoil.resource.*;
import hu.holyoil.view.frames.GameFrame;
import hu.holyoil.view.frames.MenuFrame;

import java.util.*;

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
     * A játékban lévő ufo-k száma a játék kezdetekor.
     * */
    public int numOfUfos = 3;

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
     * Elindított alkalmazás menü ablaka.
     */
    private final MenuFrame menu = new MenuFrame();

    /**
     * Fókuszba keríti a menü ablakát, játéindításra ad lehetőséget.
     */
    public void StartApp() {
        menu.setVisible(true);
    }

    static int minAsteroidCount = 100;
    static int maxAsteroidCount = 200;
    static int minLayerCount = 3;
    static int maxLayerCount = 5;
    // chance of asteroids becoming neighbours (%)
    static int chanceOfNeighbours = 10;

    /**
     * elindítja a játékot.
     * @param numOfPlayers A játékban lévő játékosok száma
     */
    public void StartGame(int numOfPlayers)  {
        Logger.Log(this,"Starting game");

        menu.setVisible(false);
        GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(true);

        // Generate between minAsteroidCount and maxAsteroidCount asteroids
        Random random = new Random();
        int numOfAsteroids = minAsteroidCount;
        if (Main.isRandomEnabled) {
            numOfAsteroids += random.nextInt(maxAsteroidCount - minAsteroidCount + 1);
        }

        String startingAsteroidName = null;
        String ufoStartingAsteroidName = null;

        for (int i = 0; i < numOfAsteroids; i++) {

            Asteroid asteroid = new Asteroid();

            // settlers start at the first asteroid
            if (startingAsteroidName == null)
                startingAsteroidName = asteroid.GetId();

            // ufos start at the last asteroid
            ufoStartingAsteroidName = asteroid.GetId();

            // this looked ugly in "one" line.
            if (Main.isRandomEnabled) {

                asteroid.SetNumOfLayersRemaining(
                        random.nextInt(maxLayerCount - minLayerCount) + minLayerCount
                );

            } else {

                asteroid.SetNumOfLayersRemaining(
                        (i % (maxLayerCount - minLayerCount + 1)) + minLayerCount
                );

            }

            asteroid.SetIsDiscovered(false);
            int generatedResource;

            if (Main.isRandomEnabled) {

                // We generate a resource. There are 4 resources, 0 means no resource.
                generatedResource = random.nextInt(5);

            } else {

                // With layers being between 3-5, and this being between 0-4, their smallest common divisor is 1.
                // Meaning that they will generate every combination of layercount and resource.
                generatedResource = i % 5;

            }
            AbstractBaseResource resource = null;

            switch (generatedResource) {
                case 1: {
                    resource = new Coal();
                    break;
                }
                case 2: {
                    resource = new Iron();
                    break;
                }
                case 3: {
                    resource = new Uranium();
                    break;
                }
                case 4: {
                    resource = new Water();
                    break;
                }
                default: {
                    // do nothing, this asteroid has no resource
                    break;
                }
            }

            asteroid.SetResource(resource);

        }

        boolean isTraversable = false;

        // we only exit the creation if there are no asteroids without neighbours
        while (!isTraversable) {

            List<String> asteroidNames = new ArrayList<>();
            AsteroidRepository.GetInstance().GetAll().forEach(
                    asteroid -> asteroidNames.add(asteroid.GetId())
            );

            for (int i = 0; i < asteroidNames.size(); i++) {

                if (Main.isRandomEnabled) {

                    for (int j = 0; j < asteroidNames.size(); j++) {

                        if (i == j) {
                            break;
                        }

                        if (random.nextInt(100) < chanceOfNeighbours) {

                            AsteroidRepository.GetInstance().Get(asteroidNames.get(i)).AddNeighbourAsteroid(
                                    AsteroidRepository.GetInstance().Get(asteroidNames.get(j))
                            );
                            AsteroidRepository.GetInstance().Get(asteroidNames.get(j)).AddNeighbourAsteroid(
                                    AsteroidRepository.GetInstance().Get(asteroidNames.get(i))
                            );

                        }

                    }

                } else {

                    int j = i + 1;
                    // if [j] would be out of bounds, we set it to 0
                    if (j == asteroidNames.size()) {
                        j = 0;
                    }

                    // we add chanceOfNeighbours amount of neighbours to every asteroid
                    while (j <= chanceOfNeighbours + i) {

                        AsteroidRepository.GetInstance().Get(asteroidNames.get(i)).AddNeighbourAsteroid(
                                AsteroidRepository.GetInstance().Get(asteroidNames.get(j))
                        );
                        AsteroidRepository.GetInstance().Get(asteroidNames.get(j)).AddNeighbourAsteroid(
                                AsteroidRepository.GetInstance().Get(asteroidNames.get(i))
                        );

                        j++;
                        // if [j] would be out of bounds, we set it to 0
                        if (j == asteroidNames.size()) {
                            j = 0;
                        }

                    }

                }

            }

            // We traverse the created graph using a BFS.
            Set<String> toVisit = new HashSet<>();
            Set<String> visited = new HashSet<>();
            toVisit.add(startingAsteroidName);

            while (toVisit.size() > 0) {

                String first = (String) toVisit.toArray()[0];
                toVisit.remove(first);

                for (Asteroid asteroid: AsteroidRepository.GetInstance().Get(first).GetNeighbours()) {

                    if (!visited.contains(asteroid.GetId()))
                        toVisit.add(asteroid.GetId());

                }

                visited.add(first);

            }

            // we only break if the graph is traversable
            if (visited.size() == AsteroidRepository.GetInstance().GetAll().size()) {

                isTraversable = true;

            }

        }

        for (int i = 0; i < numOfPlayers; i++) {

            // We generate numOfPlayers amount of settlers on the settler asteroid
            new Settler(
                    AsteroidRepository.GetInstance().Get(startingAsteroidName)
            );

        }

        // the starting asteroid is discovered because there are settlers on it
        AsteroidRepository.GetInstance().Get(startingAsteroidName).SetIsDiscovered(true);

        for (int i = 0; i < numOfUfos; i++) {

            // we generate numOfUfos amount of ufos on the ufo asteroid
            new Ufo(
                    AsteroidRepository.GetInstance().Get(ufoStartingAsteroidName)
            );

        }

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
