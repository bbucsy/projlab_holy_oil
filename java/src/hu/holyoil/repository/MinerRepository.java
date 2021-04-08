package hu.holyoil.repository;

import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.IMiner;

import java.util.ArrayList;
import java.util.List;

public class MinerRepository implements IReadRepository<IMiner> {

    private static MinerRepository minerRepository;

    public static MinerRepository GetInstance() {

        if (minerRepository == null) {
            minerRepository = new MinerRepository();
        }
        return minerRepository;

    }

    private MinerRepository() {}

    @Override
    public IMiner Get(String id) {
        if (SpaceshipBaseRepository.GetInstance().Get(id) instanceof IMiner) {
            return (IMiner) SpaceshipBaseRepository.GetInstance().Get(id);
        } else {
            return null;
        }
    }

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
