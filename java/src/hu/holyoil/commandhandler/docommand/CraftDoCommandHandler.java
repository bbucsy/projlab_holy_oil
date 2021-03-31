package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.repository.StorageCapableRepository;

public class CraftDoCommandHandler implements ICommandHandler {
    @Override
    public void Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 4) {

            System.out.println("Invalid number of arguments");
            return;

        }

        IStorageCapable iStorageCapable = StorageCapableRepository.GetInstance().Get(commandParams[1]);

        if (iStorageCapable == null) {

            System.out.println("No spaceship with storage exists with id: " + commandParams[1]);
            return;

        }

        switch (commandParams[3].toUpperCase()) {

            case "ROBOT": {

                iStorageCapable.CraftRobot();
                break;

            }

            case "TELEPORT": {

                iStorageCapable.CraftTeleportGate();
                break;

            }

            default: {

                System.out.println("Could not recognize craftable: " + commandParams[3]);
                break;

            }

        }

    }
}
