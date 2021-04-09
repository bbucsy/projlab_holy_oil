package hu.holyoil.commandhandler.addneighbourcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;

public class AddNeighbourCommandHandler implements ICommandHandler {
    @Override
    public boolean Handle(String command) {
        String[] commandParams = command.split(" ");

        if (commandParams.length < 3) {
            System.out.println("Invalid number of arguments");
            return false;
        }

        Asteroid asteroid1 = AsteroidRepository.GetInstance().Get(commandParams[1]);
        Asteroid asteroid2 = AsteroidRepository.GetInstance().Get(commandParams[2]);

        if (asteroid1 == null || asteroid2 == null) {
            System.out.println("No Asteroid exists with id(s): " + commandParams[1] + ", " + commandParams[2]);
            return false;
        }

        asteroid1.AddNeighbourAsteroid(asteroid2);
        asteroid2.AddNeighbourAsteroid(asteroid1);
        return true;
    }
}
