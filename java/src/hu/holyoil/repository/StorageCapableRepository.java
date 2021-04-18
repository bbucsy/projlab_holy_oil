package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.IStorageCapable;

import java.util.ArrayList;
import java.util.List;

/**
 * Ezen az osztályon keresztül érhető el az összes <i>IStorageCapable</i>. Minden <i>IStorageCapable</i> <i>Spaceship</i>,
 * ezért a tényleges adat nem itt van, hanem <tt>SpaceshipBaseRepository</tt>-ben.
 * Singleton osztály.
 * */
public class StorageCapableRepository implements IReadRepository<IStorageCapable> {

    /**
     * Belső instance.
     * */
    private static StorageCapableRepository storageCapableRepository;

    /**
     * Visszaadja a belső instance-t.
     * */
    public static StorageCapableRepository GetInstance() {

        if (storageCapableRepository == null) {
            storageCapableRepository = new StorageCapableRepository();
        }

        return storageCapableRepository;

    }

    /**
     * Mivel siungleton, a konstruktora privát.
     * */
    private StorageCapableRepository() {

    }

    /**
     * Visszaadja <i>id</i> azonosítójú <tt>IStorageCapable</tt>-et, ha létezik és tényleg <tt>IStorageCapable</tt>.
     * Egyébként <tt>null</tt>-t ad vissza.
     * @param id Az azonosító, amihez keressük a hozzátartozó objektumot.
     * @return Az <tt>IStorageCapable</tt>, vagy <tt>null</tt>
     * */
    @Override
    public IStorageCapable Get(String id) {
        if (SpaceshipBaseRepository.GetInstance().Get(id) instanceof IStorageCapable) {
            return (IStorageCapable) SpaceshipBaseRepository.GetInstance().Get(id);
        } else {
            return null;
        }
    }

    /**
     * Visszaad minden tárolt <tt>IStorageCapable</tt> objektumot.
     * @return Minden tárolt <tt>IStorageCapable</tt> objektum.
     * */
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
