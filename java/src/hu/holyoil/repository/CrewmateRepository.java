package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.crewmate.AbstractSpaceship;

import java.util.ArrayList;
import java.util.List;

/**
 * Ezen az osztályon keresztül érhető el az összes <i>Crewmate</i>. Minden <i>Crewmate</i> <i>Spaceship</i>,
 * ezért a tényleges adat nem itt van, hanem <tt>SpaceshipBaseRepository</tt>-ben.
 * Singleton osztály.
 * */
public class CrewmateRepository implements IReadRepository<AbstractCrewmate> {

    /**
     * Belső instance.
     * */
    private static CrewmateRepository crewmateRepository;

    /**
     * Visszaadja a belső instance-t.
     * */
    public static CrewmateRepository GetInstance() {

        if (crewmateRepository == null) {
            crewmateRepository = new CrewmateRepository();
        }
        return crewmateRepository;

    }

    /**
     * Mivel siungleton, a konstruktora privát.
     * */
    private CrewmateRepository() {}

    /**
     * Visszaadja <i>id</i> azonosítójú <tt>AbstractCrewmate</tt>-et, ha létezik és tényleg <tt>AbstractCrewmate</tt>.
     * Egyébként <tt>null</tt>-t ad vissza.
     * @param id Az azonosító, amihez keressük a hozzátartozó objektumot.
     * @return Az <tt>AbstractCrewmate</tt>, vagy <tt>null</tt>
     * */
    @Override
    public AbstractCrewmate Get(String id) {
        if (SpaceshipBaseRepository.GetInstance().Get(id) instanceof AbstractCrewmate) {
            return (AbstractCrewmate) SpaceshipBaseRepository.GetInstance().Get(id);
        } else {
            return null;
        }
    }

    /**
     * Visszaad minden tárolt <tt>AbstractCrewmate</tt> objektumot.
     * @return Minden tárolt <tt>AbstractCrewmate</tt> objektum.
     * */
    @Override
    public List<AbstractCrewmate> GetAll() {

        ArrayList<AbstractCrewmate> toReturn = new ArrayList<>();

        for (AbstractSpaceship potentialCrewmate: SpaceshipBaseRepository.GetInstance().GetAll()) {

            if (potentialCrewmate instanceof AbstractCrewmate) {
                toReturn.add((AbstractCrewmate)potentialCrewmate);
            }

        }

        return toReturn;

    }

}
