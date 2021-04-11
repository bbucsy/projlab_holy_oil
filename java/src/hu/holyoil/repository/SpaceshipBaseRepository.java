package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractSpaceship;

public class SpaceshipBaseRepository extends AbstractBaseRepository<AbstractSpaceship> {

    private SpaceshipBaseRepository() { }

    private static SpaceshipBaseRepository spaceshipBaseRepository;

    public static SpaceshipBaseRepository GetInstance() {

        if (spaceshipBaseRepository == null) {
            spaceshipBaseRepository = new SpaceshipBaseRepository();
        }

        return spaceshipBaseRepository;

    }

}
