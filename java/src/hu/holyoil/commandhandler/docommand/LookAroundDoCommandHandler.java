package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;
import hu.holyoil.repository.SpaceshipBaseRepository;
import hu.holyoil.repository.StorageCapableRepository;

public class LookAroundDoCommandHandler implements ICommandHandler {
    @Override
    public boolean Handle(String command) {

        // Has access to:
        // All discovered asteroids
        // Own storage

        // idk what else, please expand if you know

        String[] commandParams = command.split(" ");

        AbstractSpaceship abstractSpaceship = SpaceshipBaseRepository.GetInstance().Get(commandParams[1]);

        if (abstractSpaceship == null) {

            System.out.println("No spaceship exists with name: " + commandParams[1]);
            return false;

        }

        for (Asteroid asteroid: AsteroidRepository.GetInstance().GetAll()) {

            if (asteroid.IsDiscovered()) {

                System.out.println(asteroid.toString());

            }

        }

        IStorageCapable iStorageCapable = StorageCapableRepository.GetInstance().Get(commandParams[1]);

        if (iStorageCapable != null) {

            System.out.println(iStorageCapable.GetStorage().toString());

        }
        return true;

    }
}
