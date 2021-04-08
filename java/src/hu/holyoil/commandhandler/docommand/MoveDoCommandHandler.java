package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.repository.NeighbourBaseRepository;
import hu.holyoil.repository.SpaceshipBaseRepository;

public class MoveDoCommandHandler implements ICommandHandler {
    @Override
    public void Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 4) {

            System.out.println("Invalid number of arguments");
            return;

        }

        AbstractSpaceship abstractSpaceship = SpaceshipBaseRepository.GetInstance().Get(commandParams[1]);

        if (abstractSpaceship == null) {

            System.out.println("No spaceship exists with id: " + commandParams[1]);
            return;

        }

        INeighbour iNeighbour = NeighbourBaseRepository.GetInstance().Get(commandParams[3]);

        if (iNeighbour == null) {

            System.out.println("No asteroid or teleportgate exists with id: " + commandParams[3]);
            return;

        }

        abstractSpaceship.Move(iNeighbour);

    }
}
