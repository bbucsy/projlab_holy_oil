package hu.holyoil.repository;

import hu.holyoil.storage.PlayerStorage;

/**
 * <i>PlayerStorage</i>-öket tároló tároló. Singleton osztály.
 * */
public class PlayerStorageBaseRepository extends AbstractBaseRepository<PlayerStorage> {

    /**
     * Mivel siungleton, a konstruktora privát.
     * */
    private PlayerStorageBaseRepository() {}

    /**
     * Belső instance.
     * */
    private static PlayerStorageBaseRepository playerStorageBaseRepository;

    /**
     * Visszaadja a belső instance-t.
     * */
    public static PlayerStorageBaseRepository GetInstance() {

        if (playerStorageBaseRepository == null) {
            playerStorageBaseRepository = new PlayerStorageBaseRepository();
        }

        return playerStorageBaseRepository;

    }

}
