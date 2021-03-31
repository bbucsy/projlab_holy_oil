package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.Settler;

import java.util.ArrayList;
import java.util.List;

public class SettlerRepository implements IReadRepository<Settler> {

    private SettlerRepository() {}

    private static SettlerRepository settlerRepository;

    public static SettlerRepository GetInstance() {

        if (settlerRepository == null) {
            settlerRepository = new SettlerRepository();
        }

        return settlerRepository;

    }

    @Override
    public Settler Get(String id) {

        if (SpaceshipBaseRepository.GetInstance().Get(id) instanceof Settler) {
            return (Settler) SpaceshipBaseRepository.GetInstance().Get(id);
        } else {
            return null;
        }

    }

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
