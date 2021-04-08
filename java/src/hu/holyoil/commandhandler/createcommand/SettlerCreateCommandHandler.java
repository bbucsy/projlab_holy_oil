package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;
import hu.holyoil.repository.SettlerRepository;

public class SettlerCreateCommandHandler implements ICommandHandler {
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

        Settler settler = new Settler(asteroid, name);
        settler.DestroyStorage();

    }
}
