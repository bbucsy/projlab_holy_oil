package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.repository.StorageCapableRepository;

public class PlaceTeleportgateDoCommandHandler implements ICommandHandler {
    @Override
    public boolean Handle(String command) {
        String[] commandParams = command.split(" ");

        IStorageCapable iStorageCapable = StorageCapableRepository.GetInstance().Get(commandParams[1]);

        if (iStorageCapable == null) {

            System.out.println("No spaceship with storage exists with id: " + commandParams[1]);
            return false;

        }

        iStorageCapable.PlaceTeleporter();

        return true;
    }
}
