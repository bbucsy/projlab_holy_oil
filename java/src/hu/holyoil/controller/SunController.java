package hu.holyoil.controller;

import hu.holyoil.IIdentifiable;
import hu.holyoil.Main;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A nap viselkedését irányító singleton kontroller osztály. Körönként lép, ezért implementálja az ISteppable interfacet
 * <p>A PROJEKT EZEN FÁZISÁBAN MÉG NINCS KÉSZ, A TESZTELÉSHEZ NEM SZÜKSÉGES. Ennek megfelelően a dokumentáció is csak felszínes megértést nyújt, amennyi a tesztekhez kellhet.</p>
 */
public class SunController implements ISteppable, IIdentifiable {
    /**
     * A singleton osztályra ezzel lehet hivatkozni
     */
    private static SunController sunController;

    /**
     * A kontroller egyedi azonosítója
     * */
    private String id;

    /**
     * Visszaadja a kontorller egyedi azonosítóját
     * */
    public String GetId() {
        return id;
    }

    @Override
    public String toString() {
        return "SUNCONTROLLER (name:)" + id + " (turns until next sunstorm:)" + turnsUntilNextSunstorm;
    }

    /**
     * Pseudo random szám, ami a következő napviharig hátralévő köröket számolja
     * <p>körönként csökken,napvihar után újra sorsolódik</p>
     */
    private int turnsUntilNextSunstorm;
    /**
     * A pályán található összes aszteroidát tartalmazó lista
     */
    private List<Asteroid> asteroids;

    /**
     * Újraindítja a visszaszámláló turnsUntilNextSunstorm számlálót egy random számra 20 és 50 között
     */
    private void RestartCountdown() {
        Random random = new Random();
        turnsUntilNextSunstorm = random.nextInt(50 - 20 + 1) + 20;
    }

    /**
     * minden körben végrehajt egy lépést
     */
    @Override
    public void Step() {
        Logger.Log(this, "Steps" + (Main.isRandomEnabled ? "" : " - switched off!"));

        if (Main.isRandomEnabled) {
            --turnsUntilNextSunstorm;
            if (turnsUntilNextSunstorm == 0) {
                StartSunstorm();
                RestartCountdown();
            }
        }
    }

    /**
     * Hozzáad egy aszteroidát a számontartott aszteroidák listájához
     * @param asteroid hozzáadja az asteroids tagváltozóhoz
     */
    public void AddAsteroid(Asteroid asteroid)  {
        Logger.Log(this,"Adding asteroid <" +  Logger.GetName(asteroid)+ ">");
        asteroids.add(asteroid);
        Logger.Return();
    }

    /**
     * Töröl egy aszteroidát a számontartott aszteroidák listájáról
     * @param asteroid törli az asteroids tagváltozóról
     */
    public void RemoveAsteroid(Asteroid asteroid)  {
        Logger.Log(this,"Removing asteroid <" +  Logger.GetName(asteroid)+ ">");
        asteroids.remove(asteroid);
        Logger.Return();
    }

    /**
     * Amikor a turnsUntilNextSunstorm számláló eléri a 0-t, ez a metódus hívodik meg.
     * <p>Hatást gyakorol az öv aszteroidáinak nagyjából egyharmadára.</p>
     */
    public void StartSunstorm()  {
        Logger.Log(this,"Starting sunstorm");

        /* Collecting the asteroids to affect */
        Set<Asteroid> chosenAsteroids = new HashSet<>();
        Queue<Asteroid> traversingQueue = new LinkedList<>();

        Random rand = new Random();
        Asteroid startingAsteroid = asteroids.get(rand.nextInt(asteroids.size()));
        traversingQueue.add(startingAsteroid);
        chosenAsteroids.add(startingAsteroid);

        while (chosenAsteroids.size() < asteroids.size() / 3 && !traversingQueue.isEmpty()) {
            Asteroid currentAsteroid = traversingQueue.remove();

            List<Asteroid> untraversedNeighbours = currentAsteroid.GetNeighbours()
                   .stream()
                   .filter(item -> !chosenAsteroids.contains(item))
                   .collect(Collectors.toList());

            traversingQueue.addAll(untraversedNeighbours);

            for (Asteroid item : untraversedNeighbours) {
                if (chosenAsteroids.size() < asteroids.size() / 3)
                    chosenAsteroids.add(item);
                else
                    break;
            }
        }

        /* Calling reaction on collected asteroids */
        chosenAsteroids.forEach(Asteroid::ReactToSunstorm);

        Logger.Return();
    }

    /**
     * Singleton osztályhoz való hozzáférés miatt kell
     * @return visszaad egy instance-ot
     */
    public static SunController GetInstance() {

        if (sunController == null) {
            sunController = new SunController();
        }

        if (Logger.GetName(sunController) == null) {
            Logger.RegisterObject(sunController, ": SunController");
        }

        return sunController;

    }

    /**
     * Privát konstruktor
     * Nem lehet kívülről meghívni, nem lehet példányosítani.
     * <p>A 100 forduló a következő napviharig egy ad hoc szám, bármi lehet.
     * Inicializálja az aszteroidáka tároló listát.</p>
     */
    private SunController() {
        RestartCountdown();
        asteroids = new ArrayList<>();
        id = "SunController";
    }

}
