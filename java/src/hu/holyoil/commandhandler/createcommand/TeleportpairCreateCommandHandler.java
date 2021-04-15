package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.repository.AbstractBaseRepository;
import hu.holyoil.repository.AsteroidRepository;
import hu.holyoil.repository.PlayerStorageBaseRepository;
import hu.holyoil.commandhandler.Logger;
import hu.holyoil.storage.PlayerStorage;

public class TeleportpairCreateCommandHandler implements ICommandHandler {
    @Override
    public boolean Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 6) {

            System.out.println("Invalid number of arguments");
            return false;

        }

        String name1 = commandParams[2], name2 = commandParams[3];

        if (AbstractBaseRepository.IsNameUsed(name2)) {

            System.out.println("Object already exists with name: " + commandParams[3]);
            return false;

        }

        Asteroid asteroid1 = AsteroidRepository.GetInstance().Get(commandParams[4]);

        PlayerStorage playerStorage1 = PlayerStorageBaseRepository.GetInstance().Get(commandParams[4]);

        Asteroid asteroid2 = AsteroidRepository.GetInstance().Get(commandParams[5]);

        PlayerStorage playerStorage2 = PlayerStorageBaseRepository.GetInstance().Get(commandParams[5]);

        if (asteroid1 == null && playerStorage1 == null) {

            System.out.println("No asteroid or playerstorage exists with name: " + commandParams[4]);
            return false;

        }

        if (asteroid2 == null && playerStorage2 == null) {

            System.out.println("No asteroid or playerstorage exists with name: " + commandParams[5]);
            return false;

        }

        TeleportGate t1 = new TeleportGate(name1), t2 = new TeleportGate(name2);
        Logger.Log(InputOutputController.GetInstance(), "CREATE " + name1 + " and " + name2);
        t1.SetPair(t2);
        t2.SetPair(t1);

        if (asteroid1 != null) {

            if (asteroid1.GetTeleporter() != null) {

                System.out.println("Asteroid " + asteroid1.GetId() + " already has a teleporter");
                return false;

            }

            asteroid1.SetTeleporter(t1);
            t1.SetHomeAsteroid(asteroid1);
            t1.SetHomeStorage(null);

        }

        if (playerStorage1 != null) {

            if (playerStorage1.GetTeleporterCount() >= 3) {

                System.out.println("Playerstorage " + playerStorage1.GetId() + " has no space for teleporter");
                return false;

            }

            playerStorage1.AddTeleportGate(t1);
            t1.SetHomeStorage(playerStorage1);
            t1.SetHomeAsteroid(null);

        }

        if (asteroid2 != null) {

            if (asteroid2.GetTeleporter() != null) {

                System.out.println("Asteroid " + asteroid2.GetId() + " already has a teleporter");
                return false;

            }

            asteroid2.SetTeleporter(t2);
            t2.SetHomeAsteroid(asteroid2);
            t2.SetHomeStorage(null);

        }

        if (playerStorage2 != null) {

            if (playerStorage2.GetTeleporterCount() >= 3) {

                System.out.println("Playerstorage " + playerStorage2.GetId() + " has no space for teleporter");
                return false;

            }

            playerStorage2.AddTeleportGate(t2);
            t2.SetHomeAsteroid(null);
            t2.SetHomeStorage(playerStorage2);

        }

        if (commandParams.length > 6) {

            String isCrazy = commandParams[6];

            if (!(isCrazy.toUpperCase().equals("TRUE") || isCrazy.toUpperCase().equals("FALSE"))) {

                System.out.println(isCrazy + " is not a valid boolean. Write \"true\" or \"false\"");
                return false;

            }

            t1.SetIsCrazy(isCrazy.toUpperCase().equals("TRUE"));

        }

        if (commandParams.length > 7) {

            String isCrazy = commandParams[7];

            if (!(isCrazy.toUpperCase().equals("TRUE") || isCrazy.toUpperCase().equals("FALSE"))) {

                System.out.println(isCrazy + " is not a valid boolean. Write \"true\" or \"false\"");
                return false;

            }

            t2.SetIsCrazy(isCrazy.toUpperCase().equals("TRUE"));

        }

        Logger.Return();
        return true;

    }
}
