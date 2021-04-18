package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;

/**
 * Az do kezdetű parancsokat osztja tovább másik parancsfeldolgozó CommandHandler osztálynak megvalósításra.
 */
public class DoCommandHandler implements ICommandHandler {
    /**
     * Fogja a bejövő parancs sztringet, megnézi milyen specifikus do commandot kíván hívni a felhasználó,
     * és továbbhív egy másik osztály Handle függvényén.
     * pl.: craft esetén CraftDoCommandHandler Handle függvényén.
     * @param command feldolgozandó parancs sora
     * @return feldolgozás sikeressége
     */
    @Override
    public boolean Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 3) {
            System.out.println("Invalid number of arguments");
            return false;
        }

        boolean result;
        switch (commandParams[2].toUpperCase()) {

            case "LOOKAROUND": {

                result = new LookAroundDoCommandHandler().Handle(command);
                break;

            }

            case "MOVE": {

                result = new MoveDoCommandHandler().Handle(command);
                break;

            }

            case "DRILL": {

                result = new DrillDoCommandHandler().Handle(command);
                break;

            }

            case "MINE": {

                result = new MineDoCommandHandler().Handle(command);
                break;

            }

            case "PLACERESOURCE": {

                result = new PlaceResourceDoCommandHandler().Handle(command);
                break;

            }

            case "CRAFT": {

                result = new CraftDoCommandHandler().Handle(command);
                break;

            }

            case "PLACETELEPORTGATE": {

                result = new PlaceTeleportgateDoCommandHandler().Handle(command);
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
