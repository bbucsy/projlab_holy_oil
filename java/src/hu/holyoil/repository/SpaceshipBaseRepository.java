package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractSpaceship;

/**
 * <i>AbstractSpaceship</i>-öket tároló tároló. Singleton osztály.
 * */
public class SpaceshipBaseRepository extends AbstractBaseRepository<AbstractSpaceship> {

    /**
     * Mivel siungleton, a konstruktora privát.
     * */
    private SpaceshipBaseRepository() { }

    /**
     * Belső instance.
     * */
    private static SpaceshipBaseRepository spaceshipBaseRepository;

    /**
     * Visszaadja a belső instance-t.
     * */
    public static SpaceshipBaseRepository GetInstance() {

        if (spaceshipBaseRepository == null) {
            spaceshipBaseRepository = new SpaceshipBaseRepository();
        }

        return spaceshipBaseRepository;

    }

}
