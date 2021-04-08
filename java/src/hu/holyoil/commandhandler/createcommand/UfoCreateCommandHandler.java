package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.crewmate.Ufo;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;

public class UfoCreateCommandHandler implements ICommandHandler {
    @Override
    public void Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 4) {

            System.out.println("Invalid number of arguments");
            return;

        }

        String name = commandParams[2];

        Asteroid asteroid = AsteroidRepository.GetInstance().Get(commandParams[3]);

        if (asteroid == null) {

            System.out.println("No asteroid exists with name: " + commandParams[3]);
            return;

        }

        Ufo ufo = new Ufo(asteroid, name);

    }
}
