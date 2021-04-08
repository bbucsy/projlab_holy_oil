package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.neighbour.Asteroid;

public class AsteroidCreateCommandHandler implements ICommandHandler {
    @Override
    public void Handle(String command) {

        String[] commandParams = command.split(" ");

        String name = commandParams[2];

        Asteroid asteroid = new Asteroid(name);

        if (commandParams.length > 3) {

            String layerAmount = commandParams[3];

            try {

                int layerCount = Integer.parseInt(layerAmount);

                if (layerCount < 0 || layerCount > 5) {

                    System.out.println("Number of layers must be between 0 and 5");
                    return;

                }

                asteroid.SetNumOfLayersRemaining(layerCount);

            } catch (NumberFormatException ex) {

                System.out.println(layerAmount + " is not a valid integer");

                return;
            }

        }

        if (commandParams.length > 4) {

            String nearSun = commandParams[4];

            if (!(nearSun.toUpperCase().equals("TRUE") || nearSun.toUpperCase().equals("FALSE"))) {

                System.out.println(nearSun + " is not a valid boolean. Write \"true\" or \"false\"");
                return;

            }

            asteroid.SetIsNearbySun(nearSun.toUpperCase().equals("TRUE"));

        }

        if (commandParams.length > 5) {

            String isDiscovered = commandParams[4];

            if (!(isDiscovered.toUpperCase().equals("TRUE") || isDiscovered.toUpperCase().equals("FALSE"))) {

                System.out.println(isDiscovered + " is not a valid boolean. Write \"true\" or \"false\"");
                return;

            }

            asteroid.SetIsDiscovered(isDiscovered.toUpperCase().equals("TRUE"));

        }

    }
}
