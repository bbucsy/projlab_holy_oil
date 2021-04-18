package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;
import hu.holyoil.repository.SpaceshipBaseRepository;
import hu.holyoil.repository.StorageCapableRepository;

/**
 * Az `do id craft params` parancs megvalósítása.
 */
public class LookAroundDoCommandHandler implements ICommandHandler {
    /**
     * Megmutatja a felfedezett és környező aszteroidákat és teleportkaput, azok tulajdonságait, illetve a storage-unkat.
     *
     * @param command feldolgozandó parancs sora
     * @return feldolgozás sikeressége
     */
    @Override
    public boolean Handle(String command) {

        // Has access to:
        // All discovered asteroids
        // Own storage

        String[] commandParams = command.split(" ");

        AbstractSpaceship abstractSpaceship = SpaceshipBaseRepository.GetInstance().Get(commandParams[1]);

        if (abstractSpaceship == null) {

            System.out.println("No spaceship exists with name: " + commandParams[1]);
            return false;

        }

        for (Asteroid asteroid: AsteroidRepository.GetInstance().GetAll()) {

            if (asteroid.IsDiscovered() ||
                    abstractSpaceship.GetOnAsteroid().GetNeighbours().contains(asteroid)
            ) {

                System.out.println(asteroid.toString());

                if (asteroid.GetTeleporter() != null) {

                    System.out.println(asteroid.GetTeleporter().toString());

                }

            }

        }

        IStorageCapable iStorageCapable = StorageCapableRepository.GetInstance().Get(commandParams[1]);

        if (iStorageCapable != null) {

            System.out.println(iStorageCapable.GetStorage().toString());

        }
        return true;

    }
}
