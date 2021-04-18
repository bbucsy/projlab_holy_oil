package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.repository.StorageCapableRepository;

/**
 * Az `do id craft params` parancs megvalósítása.
 */
public class CraftDoCommandHandler implements ICommandHandler {
    /**
     * Megnézi, ki akar működni, ha IStorageCapable, továbbengedi, illetve azt is nézi, hogy milyen eszközt próbál létrehozni.
     * Ezután meghívja a megfelelő IStorageCapable-ön a megfelelő craftoló függvényt.
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
        return true;

    }
}
