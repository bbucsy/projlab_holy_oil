package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.Settler;

import java.util.ArrayList;
import java.util.List;

/**
 * Ezen az osztályon keresztül érhető el az összes <i>Settler</i>. Minden <i>Settler</i> <i>Spaceship</i>,
 * ezért a tényleges adat nem itt van, hanem <tt>SpaceshipBaseRepository</tt>-ben.
 * Singleton osztály.
 * */
public class SettlerRepository implements IReadRepository<Settler> {

    /**
     * Mivel siungleton, a konstruktora privát.
     * */
    private SettlerRepository() {}

    /**
     * Belső instance.
     * */
    private static SettlerRepository settlerRepository;

    /**
     * Visszaadja a belső instance-t.
     * */
    public static SettlerRepository GetInstance() {

        if (settlerRepository == null) {
            settlerRepository = new SettlerRepository();
        }

        return settlerRepository;

    }

    /**
     * Visszaadja <i>id</i> azonosítójú <tt>Settler</tt>-et, ha létezik és tényleg <tt>Settler</tt>.
     * Egyébként <tt>null</tt>-t ad vissza.
     * @param id Az azonosító, amihez keressük a hozzátartozó objektumot.
     * @return Az <tt>Settler</tt>, vagy <tt>null</tt>
     * */
    @Override
    public Settler Get(String id) {

        if (SpaceshipBaseRepository.GetInstance().Get(id) instanceof Settler) {
            return (Settler) SpaceshipBaseRepository.GetInstance().Get(id);
        } else {
            return null;
        }

    }

    /**
     * Visszaad minden tárolt <tt>Settler</tt> objektumot.
     * @return Minden tárolt <tt>Settler</tt> objektum.
     * */
    @Override
    public List<Settler> GetAll() {

        ArrayList<Settler> toReturn = new ArrayList<>();

        for (AbstractSpaceship potentialSettler: SpaceshipBaseRepository.GetInstance().GetAll()) {

            if (potentialSettler instanceof Settler) {
                toReturn.add((Settler)potentialSettler);
            }

        }

        return toReturn;

    }

}
