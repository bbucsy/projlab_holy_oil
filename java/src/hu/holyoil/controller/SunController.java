package hu.holyoil.controller;

import hu.holyoil.IIdentifiable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;

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
        return "SUNCONTROLLER (name:) " + id + "\n\t(turns until next sunstorm:) " + turnsUntilNextSunstorm;
    }

    /**
     * Pseudo random szám, ami a következő napviharig hátralévő köröket számolja
     * <p>körönként csökken,napvihar után újra sorsolódik</p>
     */
    private int turnsUntilNextSunstorm;

    /**
     * Újraindítja a visszaszámláló turnsUntilNextSunstorm számlálót egy random számra 20 és 50 között
     * Random kikapcsolásával 30-ra állítódik.
     */
    private void RestartCountdown() {
        Random random = new Random();
        turnsUntilNextSunstorm = random.nextInt(50 - 20 + 1) + 20;
    }

    /**
     * Minden körben végrehajt egy lépést
     */
    @Override
    public void Step() {
        --turnsUntilNextSunstorm;
        if (turnsUntilNextSunstorm == 0) {
            StartSunstorm();
            RestartCountdown();
        }
        if(turnsUntilNextSunstorm<=3){
            Logger.Log(this, "Sunstorm in " + turnsUntilNextSunstorm + " turn(s)");
        }
        PullAsteroids();
        List<Asteroid> asteroidsShallowCopy = new ArrayList<>(AsteroidRepository.GetInstance().GetAll());
        asteroidsShallowCopy.forEach(Asteroid::ReactToSunNearby);
    }

    /**
     * 25% valószínűséggel napközelbe helyez minden aszteroidát.
     */
    public void PullAsteroids(){
        List<Asteroid> asteroids = AsteroidRepository.GetInstance().GetAll();
        Random random = new Random();
        asteroids.forEach(asteroid -> {
            asteroid.SetIsNearbySun(random.nextDouble() < 0.25);
        });
    }

    /**
     * Amikor a turnsUntilNextSunstorm számláló eléri a 0-t, ez a metódus hívodik meg.
     * <p>Hatást gyakorol az öv aszteroidáinak nagyjából egyharmadára.</p>
     */
    public void StartSunstorm()  {
        List<Asteroid> asteroids = AsteroidRepository.GetInstance().GetAll();

        /* Collecting the asteroids to affect */
        Set<Asteroid> chosenAsteroids = new HashSet<>();
        Queue<Asteroid> traversingQueue = new LinkedList<>();

        Random rand = new Random();
        Asteroid startingAsteroid = asteroids.get(rand.nextInt(asteroids.size()));
        traversingQueue.add(startingAsteroid);
        chosenAsteroids.add(startingAsteroid);

        while (chosenAsteroids.size() < asteroids.size() / 2 && !traversingQueue.isEmpty()) {
            Asteroid currentAsteroid = traversingQueue.remove();

            List<Asteroid> untraversedNeighbours = currentAsteroid.GetNeighbours()
                   .stream()
                   .filter(item -> !chosenAsteroids.contains(item))
                   .collect(Collectors.toList());

            traversingQueue.addAll(untraversedNeighbours);

            for (Asteroid item : untraversedNeighbours) {
                if (chosenAsteroids.size() < asteroids.size() / 2)
                    chosenAsteroids.add(item);
                else
                    break;
            }
        }

        /* Calling reaction on collected asteroids */
        chosenAsteroids.forEach(Asteroid::ReactToSunstorm);
    }

    /**
     * Visszaadja hány kör van még a következő napciharig.
     * @return int, turnsUntilNextSunstorm
     */
    public int GetTurnsUntilStorm(){
        return turnsUntilNextSunstorm;
    }

    /**
     * Singleton osztályhoz való hozzáférés miatt kell.
     * @return visszaad egy instance-ot
     */
    public static SunController GetInstance() {

        if (sunController == null) {
            sunController = new SunController();
            Logger.RegisterObject(sunController, sunController.id);
        }

        return sunController;

    }

    /**
     * Privát konstruktor.
     * Nem lehet kívülről meghívni, nem lehet példányosítani.
     * <p>A 100 forduló a következő napviharig egy ad hoc szám, bármi lehet.
     * Inicializálja az aszteroidáka tároló listát.</p>
     */
    private SunController() {
        RestartCountdown();
        id = "Sun";
    }

}
