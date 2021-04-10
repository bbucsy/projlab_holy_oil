package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.IStorageCapable;

import java.util.ArrayList;
import java.util.List;

public class StorageCapableRepository implements IReadRepository<IStorageCapable> {

    private static StorageCapableRepository storageCapableRepository;

    public static StorageCapableRepository GetInstance() {

        if (storageCapableRepository == null) {
            storageCapableRepository = new StorageCapableRepository();
        }

        return storageCapableRepository;

    }

    private StorageCapableRepository() {

    }

    @Override
    public IStorageCapable Get(String id) {
        if (SpaceshipBaseRepository.GetInstance().Get(id) instanceof IStorageCapable) {
            return (IStorageCapable) SpaceshipBaseRepository.GetInstance().Get(id);
        } else {
            return null;
        }
    }

    @Override
    public List<IStorageCapable> GetAll() {
        ArrayList<IStorageCapable> toReturn = new ArrayList<>();

        for (AbstractSpaceship potentialStorageCapable: SpaceshipBaseRepository.GetInstance().GetAll()) {

            if (potentialStorageCapable instanceof IStorageCapable) {
                toReturn.add((IStorageCapable)potentialStorageCapable);
            }

        }

        return toReturn;
    }
}
