package hu.holyoil.repository;

import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.storage.PlayerStorage;

import java.util.HashMap;

public class PlayerStorageBaseRepository extends AbstractBaseRepository<PlayerStorage> {

    HashMap<String, INeighbour> playerStorages;

    private PlayerStorageBaseRepository() {
        playerStorages = new HashMap<>();
    }

    private static PlayerStorageBaseRepository playerStorageBaseRepository;

    public static PlayerStorageBaseRepository GetInstance() {

        if (playerStorageBaseRepository == null) {
            playerStorageBaseRepository = new PlayerStorageBaseRepository();
        }

        return playerStorageBaseRepository;

    }

}
