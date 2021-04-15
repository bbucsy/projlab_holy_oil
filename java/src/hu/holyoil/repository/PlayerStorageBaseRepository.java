package hu.holyoil.repository;

import hu.holyoil.storage.PlayerStorage;

public class PlayerStorageBaseRepository extends AbstractBaseRepository<PlayerStorage> {


    private PlayerStorageBaseRepository() {}

    private static PlayerStorageBaseRepository playerStorageBaseRepository;

    public static PlayerStorageBaseRepository GetInstance() {

        if (playerStorageBaseRepository == null) {
            playerStorageBaseRepository = new PlayerStorageBaseRepository();
        }

        return playerStorageBaseRepository;

    }

}
