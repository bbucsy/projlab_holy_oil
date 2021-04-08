package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.IStorageCapable;

import java.util.ArrayList;
import java.util.List;

public class StorageCapableRepository implements IReadWriteRepository<IStorageCapable> {

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
    public void Add(String name, IStorageCapable element) {
        // Cannot add IStorageCapable directly
        // Does nothing
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

    @Override
    public void Remove(String id) {
        // Cannot remove IStorageCapable directly
        // Does nothing
    }
}
