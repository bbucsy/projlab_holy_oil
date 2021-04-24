package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.repository.CrewmateRepository;

/**
 * Az `do id craft params` parancs megvalósítása.
 */
public class DrillDoCommandHandler implements ICommandHandler {
    /**
     * A kért id-jű entitással megcsináltatja a fúrást a jelenlegi aszteroidáján, már ha az irányítani kívánt entitás AbstractCrewmate.
     *
     * @param command feldolgozandó parancs sora
     * @return feldolgozás sikeressége
     */
    @Override
    public boolean Handle(String command) {

        String[] commandParams = command.split(" ");

        AbstractCrewmate abstractCrewmate = CrewmateRepository.GetInstance().Get(commandParams[1]);

        if (abstractCrewmate == null) {

            System.out.println("No crewmate exists with id: " + commandParams[1]);
            return false;

        }

        abstractCrewmate.Drill();

        return true;
    }
}
