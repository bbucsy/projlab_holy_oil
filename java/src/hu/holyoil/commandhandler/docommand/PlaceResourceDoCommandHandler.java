package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.repository.ResourceBaseRepository;
import hu.holyoil.repository.StorageCapableRepository;
import hu.holyoil.resource.AbstractBaseResource;

/**
 * Az `do id craft params` parancs megvalósítása.
 */
public class PlaceResourceDoCommandHandler implements ICommandHandler {
    /**
     * Fogja a kért nyersanyagot, kikeresi, és lerakatja az IStorageCapable-lel.
     *
     * @param command feldolgozandó parancs sora
     * @return feldolgozás sikeressége
     */
    @Override
    public boolean Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 4) {

            System.out.println("Invalid number of arguments");
            return false;

        }

        IStorageCapable iStorageCapable = StorageCapableRepository.GetInstance().Get(commandParams[1]);

        if (iStorageCapable == null) {

            System.out.println("No spaceship with storage exists with id: " + commandParams[1]);
            return false;

        }

        AbstractBaseResource abstractBaseResource = ResourceBaseRepository.GetInstance().Get(commandParams[3]);

        if (abstractBaseResource == null) {

            System.out.println("No resource exists with id: " + commandParams[3]);

        }

        iStorageCapable.PlaceResource(abstractBaseResource);

        return true;
    }
}
