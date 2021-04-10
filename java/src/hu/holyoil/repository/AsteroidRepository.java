package hu.holyoil.repository;

import hu.holyoil.Main;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hu.holyoil.repository.AbstractBaseRepository.IsNameUsed;

public class AsteroidRepository implements IReadWriteRepository<Asteroid> {

    private static AsteroidRepository asteroidRepository;

    private AsteroidRepository() {

    }

    public static AsteroidRepository GetInstance() {

        if (asteroidRepository == null) {
            asteroidRepository = new AsteroidRepository();
        }

        return asteroidRepository;

    }

    @Override
    public Asteroid Get(String id) {
        if (NeighbourBaseRepository.GetInstance().Get(id) instanceof Asteroid) {
            return (Asteroid) NeighbourBaseRepository.GetInstance().Get(id);
        } else {
            return null;
        }
    }

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

    @Override
    public void Add(String name, Asteroid element) {
        NeighbourBaseRepository.GetInstance().Add(name, element);
    }

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

                Random random = new Random();

                int chosenId = 0;
                if (Main.isRandomEnabled)
                    chosenId = random.nextInt(asteroids.size());

                // we search for a random asteroid
                while (asteroids.get(chosenId).equals(asteroid) || asteroids.get(chosenId).equals(as)) {
                    if (Main.isRandomEnabled)
                        chosenId = random.nextInt(asteroids.size());
                    else
                        chosenId++;
                    if (chosenId == asteroids.size()) {
                        return false;
                    }
                }

                // and make them neighbours
                asteroids.get(chosenId).AddNeighbourAsteroid(as);
                as.AddNeighbourAsteroid(asteroids.get(chosenId));

            }

        }

        return true;

    }
}
