package hu.holyoil.repository;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;

import java.util.ArrayList;
import java.util.List;

public class AsteroidRepository implements IReadRepository<Asteroid> {

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

}
