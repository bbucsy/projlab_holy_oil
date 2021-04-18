package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.repository.AbstractBaseRepository;

/**
 * Az create kezdetű parancsokat osztja tovább másik parancsfeldolgozó CommandHandler osztálynak megvalósításra.
 */
public class CreateCommandHandler implements ICommandHandler {
    /**
     * Fogja a bejövő parancs sztringet, megnézi milyen specifikus create commandot kíván hívni a felhasználó,
     * és továbbhív egy másik osztály Handle függvényén,
     * pl.: asteroid esetén AsteroidCreateCommandHandler Handle függvényén.
     *
     * @param command feldolgozandó parancs
     * @return parancs lefutásának sikeressége
     */
    @Override
    public boolean Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 3) {

            System.out.println("Invalid number of arguments");
            return false;

        }

        if (AbstractBaseRepository.IsNameUsed(commandParams[2])) {

            System.out.println("Object already exists with name: " + commandParams[2]);
            return false;

        }

        boolean result;
        switch (commandParams[1].toUpperCase()) {

            case "ASTEROID": {

                result = new AsteroidCreateCommandHandler().Handle(command);
                break;

            }

            case "COAL": {

                result = new CoalCreateCommandHandler().Handle(command);
                break;

            }

            case "IRON": {

                result = new IronCreateCommandHandler().Handle(command);
                break;

            }

            case "STORAGE": {

                result = new PlayerStorageCreateCommandHandler().Handle(command);
                break;

            }

            case "ROBOT": {

                result = new RobotCreateCommandHandler().Handle(command);
                break;

            }

            case "SETTLER": {

                result = new SettlerCreateCommandHandler().Handle(command);
                break;

            }

            case "TELEPORTPAIR": {

                result = new TeleportpairCreateCommandHandler().Handle(command);
                break;

            }

            case "UFO": {

                result = new UfoCreateCommandHandler().Handle(command);
                break;

            }

            case "URANIUM": {

                result = new UraniumCreateCommandHandler().Handle(command);
                break;

            }

            case "WATER": {

                result = new WaterCreateCommandHandler().Handle(command);
                break;

            }

            default: {

                System.out.println("Command not recognized: " + commandParams[2]);
                result = false;
                break;

            }

        }

        return result;
    }
}
