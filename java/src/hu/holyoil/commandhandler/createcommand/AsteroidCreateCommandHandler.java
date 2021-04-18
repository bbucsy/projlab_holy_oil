package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.commandhandler.Logger;

/**
 * A `create asteroid name params` parancs megvalósítása.
 */
public class AsteroidCreateCommandHandler implements ICommandHandler {
    /**
     * A kért aszteroidát létrehozza, a játékba regisztrálja
     *
     * @param command feldolgozandó parancs
     * @return parancs lefutásának sikeressége
     */
    @Override
    public boolean Handle(String command) {

        String[] commandParams = command.split(" ");

        String name = commandParams[2];

        Asteroid asteroid = new Asteroid(name);
        Logger.Log(InputOutputController.GetInstance(), "CREATE " + name);

        if (commandParams.length > 3) {

            String layerAmount = commandParams[3];

            try {

                int layerCount = Integer.parseInt(layerAmount);

                if (layerCount < 0 || layerCount > 5) {

                    System.out.println("Number of layers must be between 0 and 5");
                    return false;

                }

                asteroid.SetNumOfLayersRemaining(layerCount);

            } catch (NumberFormatException ex) {

                System.out.println(layerAmount + " is not a valid integer");

                return false;
            }

        }

        if (commandParams.length > 4) {

            String nearSun = commandParams[4];

            if (!(nearSun.toUpperCase().equals("TRUE") || nearSun.toUpperCase().equals("FALSE"))) {

                System.out.println(nearSun + " is not a valid boolean. Write \"true\" or \"false\"");
                return false;

            }

            asteroid.SetIsNearbySun(nearSun.toUpperCase().equals("TRUE"));

        }

        if (commandParams.length > 5) {

            String isDiscovered = commandParams[4];

            if (!(isDiscovered.toUpperCase().equals("TRUE") || isDiscovered.toUpperCase().equals("FALSE"))) {

                System.out.println(isDiscovered + " is not a valid boolean. Write \"true\" or \"false\"");
                return false;

            }

            asteroid.SetIsDiscovered(isDiscovered.toUpperCase().equals("TRUE"));

        }

        Logger.Return();
        return true;
    }
}
