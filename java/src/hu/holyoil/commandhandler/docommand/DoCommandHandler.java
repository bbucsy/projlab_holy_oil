package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;

public class DoCommandHandler implements ICommandHandler {

    @Override
    public boolean Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 3) {
            System.out.println("Invalid number of arguments");
            return false;
        }

        switch (commandParams[2].toUpperCase()) {

            case "LOOKAROUND": {

                new LookAroundDoCommandHandler().Handle(command);
                break;

            }

            case "MOVE": {

                new MoveDoCommandHandler().Handle(command);
                break;

            }

            case "DRILL": {

                new DrillDoCommandHandler().Handle(command);
                break;

            }

            case "MINE": {

                new MineDoCommandHandler().Handle(command);
                break;

            }

            case "PLACERESOURCE": {

                new PlaceResourceDoCommandHandler().Handle(command);
                break;

            }

            case "CRAFT": {

                new CraftDoCommandHandler().Handle(command);
                break;

            }

            case "PLACETELEPORTGATE": {

                new PlaceTeleportgateDoCommandHandler().Handle(command);
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
