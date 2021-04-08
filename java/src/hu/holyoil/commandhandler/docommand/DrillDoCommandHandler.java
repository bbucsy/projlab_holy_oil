package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.repository.CrewmateRepository;

public class DrillDoCommandHandler implements ICommandHandler {
    @Override
    public void Handle(String command) {

        String[] commandParams = command.split(" ");

        AbstractCrewmate abstractCrewmate = CrewmateRepository.GetInstance().Get(commandParams[1]);

        if (abstractCrewmate == null) {

            System.out.println("No crewmate exists with id: " + commandParams[1]);
            return;

        }

        abstractCrewmate.Drill();

    }
}
