package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.repository.AbstractBaseRepository;

public class CreateCommandHandler implements ICommandHandler {
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

        switch (commandParams[1].toUpperCase()) {

            case "ASTEROID": {

                new AsteroidCreateCommandHandler().Handle(command);
                break;

            }

            case "COAL": {

                new CoalCreateCommandHandler().Handle(command);
                break;

            }

            case "IRON": {

                new IronCreateCommandHandler().Handle(command);
                break;

            }

            case "STORAGE": {

                new PlayerStorageCreateCommandHandler().Handle(command);
                break;

            }

            case "ROBOT": {

                new RobotCreateCommandHandler().Handle(command);
                break;

            }

            case "SETTLER": {

                new SettlerCreateCommandHandler().Handle(command);
                break;

            }

            case "TELEPORTPAIR": {

                new TeleportpairCreateCommandHandler().Handle(command);
                break;

            }

            case "UFO": {

                new UfoCreateCommandHandler().Handle(command);
                break;

            }

            case "URANIUM": {

                new UraniumCreateCommandHandler().Handle(command);
                break;

            }

            case "WATER": {

                new WaterCreateCommandHandler().Handle(command);
                break;

            }

            default: {

                System.out.println("Command not recognized: " + commandParams[2]);
                break;

            }

        }

        return true;
    }
}
