package hu.holyoil.repository;

import hu.holyoil.Main;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;

import java.util.*;

/**
 * Aszteroidákat tároló tároló. Aszteroida törlése speciális logikát is tartalmaz, ezért ez
 * nem csak olvasható, hanem olvasható és írható. Singleton osztály.
 * */
public class AsteroidRepository implements IReadWriteRepository<Asteroid> {

    /**
     * Belső instance.
     * */
    private static AsteroidRepository asteroidRepository;

    /**
     * Mivel ez singleton, a konstruktora privát.
     * */
    private AsteroidRepository() {

    }

    /**
     * Visszaadja a singleton példányt.
     * @return A repository példány.
     * */
    public static AsteroidRepository GetInstance() {

        if (asteroidRepository == null) {
            asteroidRepository = new AsteroidRepository();
        }

        return asteroidRepository;

    }

    /**
     * Viszaadja az <i>id</i> azonosítójú aszteroidát, amennyiben <b>létezik</b> és <b>tényleg aszteroida</b>,
     * egyébként <tt>null</tt>-t ad vissza.
     * @param id Az aszteroida azonosítója
     * @return Az aszteroida, vagy <tt>null</tt>
     * */
    @Override
    public Asteroid Get(String id) {
        if (NeighbourBaseRepository.GetInstance().Get(id) instanceof Asteroid) {
            return (Asteroid) NeighbourBaseRepository.GetInstance().Get(id);
        } else {
            return null;
        }
    }

    /**
     * Viszaad minden játékban lévő aszteroidát.
     * @return Minden aszteroida.
     * */
    @Override
    public List<Asteroid> GetAll() {

        ArrayList<Asteroid> toReturn = new ArrayList<>();

        for (INeighbour potentialAsteroid: NeighbourBaseRepository.GetInstance().GetAll()) {

            if (potentialAsteroid instanceof Asteroid) {
                toReturn.add((Asteroid)potentialAsteroid);
            }

        }

        return toReturn;

    }

    /**
     * Hozzáad egy <i>name</i> azonosítójú <i>element</i> aszteroidát. Az aszteroidák <tt>NeighbourBaseRepository</tt>-ben
     * vannak tárolva, így ez a hívás oda továbbítódik.
     * */
    @Override
    public void Add(String name, Asteroid element) {
        NeighbourBaseRepository.GetInstance().Add(name, element);
    }

    /**
     * Töröl egy <i>id</i> azonosítójú aszteroidát. Aszteroida törlésénél ellenőrzi,
     * hogy ez az aszteroida egy másik aszteroidának utolsó szomszédja-e, és amennyiben igen,
     * annak generál egy újabb szomszédot.
     * @param id A törölni kívánt aszteroida azonosítója.
     * @return A törlés sikeressége.
     * */
    @Override
    public boolean Remove(String id) {

        boolean couldRemove = NeighbourBaseRepository.GetInstance().Remove(id);

        if (!couldRemove) {
            return false;
        }

        Asteroid asteroid = Get(id);
        List<Asteroid> asteroids = GetAll();

        for (Asteroid as: GetAll()) {

            // if this asteroid has only one neighbour and it is me
            if ((as.GetNeighbours().contains(asteroid) && as.GetNeighbours().size() == 1) || as.GetNeighbours().size() == 0) {

                boolean isTraversable = false;

                while (!isTraversable) {

                    Random random = new Random();

                    int chosenId = 0;
                    if (Main.isRandomEnabled)
                        chosenId = random.nextInt(asteroids.size());

                    // We avoid an infinite loop
                    int loopIterations = 0;
                    /* we search for an asteroid that:
                     * - is not me
                     * - is not already my neighbour
                     * */
                    while (asteroids.get(chosenId).equals(asteroid) || asteroids.get(chosenId).equals(as) || as.GetNeighbours().contains(asteroids.get(chosenId))) {
                        if (Main.isRandomEnabled)
                            chosenId = random.nextInt(asteroids.size());
                        else
                            chosenId++;
                        loopIterations++;
                        if (chosenId == asteroids.size() || loopIterations >= 1000) {
                            return false;
                        }
                    }

                    // and make them neighbours
                    asteroids.get(chosenId).AddNeighbourAsteroid(as);
                    as.AddNeighbourAsteroid(asteroids.get(chosenId));

                    // We check if this graph is connected
                    Set<String> toVisit = new HashSet<>();
                    Set<String> visited = new HashSet<>();
                    toVisit.add(as.GetId());

                    while (toVisit.size() > 0) {

                        String first = (String) toVisit.toArray()[0];
                        toVisit.remove(first);

                        for (Asteroid neighbour: AsteroidRepository.GetInstance().Get(first).GetNeighbours()) {

                            if (!visited.contains(neighbour.GetId()))
                                toVisit.add(neighbour.GetId());

                        }

                        visited.add(first);

                    }

                    // we only break if the graph is traversable
                    if (visited.size() == AsteroidRepository.GetInstance().GetAll().size()) {

                        isTraversable = true;

                    }

                }

            }

        }

        return true;

    }
}
