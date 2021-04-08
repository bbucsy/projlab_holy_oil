package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractSpaceship;

import java.util.HashMap;

public class SpaceshipBaseRepository extends AbstractBaseRepository<AbstractSpaceship> {

    HashMap<String, AbstractSpaceship> abstractSpaceships;

    private SpaceshipBaseRepository() {
        abstractSpaceships = new HashMap<>();
    }

    private static SpaceshipBaseRepository spaceshipBaseRepository;

    public static SpaceshipBaseRepository GetInstance() {

        if (spaceshipBaseRepository == null) {
            spaceshipBaseRepository = new SpaceshipBaseRepository();
        }

        return spaceshipBaseRepository;

    }

}
