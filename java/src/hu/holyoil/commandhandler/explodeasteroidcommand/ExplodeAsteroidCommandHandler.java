package hu.holyoil.commandhandler.explodeasteroidcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;

public class ExplodeAsteroidCommandHandler implements ICommandHandler {
    @Override
    public void Handle(String command) {
        String[] commandParams = command.split(" ");
        if (commandParams.length < 2) {
            System.out.println("Invalid number of arguments");
            return;
        }
        Asteroid asteroid = AsteroidRepository.GetInstance().Get(commandParams[1]);
        if (asteroid == null) {
            System.out.println("No Asteroid exists with id: " + commandParams[1]);
            return;
        }
        asteroid.Explode();
    }
}
