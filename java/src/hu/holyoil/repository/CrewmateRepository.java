package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.crewmate.AbstractSpaceship;

import java.util.ArrayList;
import java.util.List;

public class CrewmateRepository implements IReadRepository<AbstractCrewmate> {

    private static CrewmateRepository crewmateRepository;

    public static CrewmateRepository GetInstance() {

        if (crewmateRepository == null) {
            crewmateRepository = new CrewmateRepository();
        }
        return crewmateRepository;

    }

    private CrewmateRepository() {}

    @Override
    public AbstractCrewmate Get(String id) {
        if (SpaceshipBaseRepository.GetInstance().Get(id) instanceof AbstractCrewmate) {
            return (AbstractCrewmate) SpaceshipBaseRepository.GetInstance().Get(id);
        } else {
            return null;
        }
    }

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
