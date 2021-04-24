package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.IMiner;

import java.util.ArrayList;
import java.util.List;

/**
 * Ezen az osztályon keresztül érhető el az összes <i>IMiner</i>. Minden <i>IMiner</i> <i>Spaceship</i>,
 * ezért a tényleges adat nem itt van, hanem <tt>SpaceshipBaseRepository</tt>-ben.
 * Singleton osztály.
 * */
public class MinerRepository implements IReadRepository<IMiner> {

    /**
     * Belső instance.
     * */
    private static MinerRepository minerRepository;

    /**
     * Visszaadja a belső instance-t.
     * */
    public static MinerRepository GetInstance() {

        if (minerRepository == null) {
            minerRepository = new MinerRepository();
        }
        return minerRepository;

    }

    /**
     * Mivel siungleton, a konstruktora privát.
     * */
    private MinerRepository() {}

    /**
     * Visszaadja <i>id</i> azonosítójú <tt>IMiner</tt>-t, ha létezik és tényleg <tt>IMiner</tt>.
     * Egyébként <tt>null</tt>-t ad vissza.
     * @param id Az azonosító, amihez keressük a hozzátartozó objektumot.
     * @return Az <tt>IMiner</tt>, vagy <tt>null</tt>
     * */
    @Override
    public IMiner Get(String id) {
        if (SpaceshipBaseRepository.GetInstance().Get(id) instanceof IMiner) {
            return (IMiner) SpaceshipBaseRepository.GetInstance().Get(id);
        } else {
            return null;
        }
    }

    /**
     * Visszaad minden tárolt <tt>IMiner</tt> objektumot.
     * @return Minden tárolt <tt>IMiner</tt> objektum.
     * */
    @Override
    public List<IMiner> GetAll() {

        ArrayList<IMiner> toReturn = new ArrayList<>();

        for (AbstractSpaceship potentialMiner: SpaceshipBaseRepository.GetInstance().GetAll()) {

            if (potentialMiner instanceof IMiner) {
                toReturn.add((IMiner)potentialMiner);
            }

        }

        return toReturn;

    }
}
