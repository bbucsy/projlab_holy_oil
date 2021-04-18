package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.commandhandler.Logger;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.crewmate.Ufo;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;

/**
 * Az `create ufo name where` parancs megvalósítása.
 */
public class UfoCreateCommandHandler implements ICommandHandler {
    /**
     * Létrehozza, regisztrálja a világban az ufót, majd elhelyezi ahova szeretnénk.
     *
     * @param command feldolgozandó parancs
     * @return parancs lefutásának sikeressége
     */
    @Override
    public boolean Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 4) {

            System.out.println("Invalid number of arguments");
            return false;

        }

        String name = commandParams[2];

        Asteroid asteroid = AsteroidRepository.GetInstance().Get(commandParams[3]);

        if (asteroid == null) {

            System.out.println("No asteroid exists with name: " + commandParams[3]);
            return false;

        }

        Ufo ufo = new Ufo(asteroid, name);
        Logger.Log(InputOutputController.GetInstance(), "CREATE " + name);
        Logger.Return();

        return true;
    }
}
