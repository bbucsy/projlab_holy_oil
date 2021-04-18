package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.commandhandler.Logger;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;

public class RobotCreateCommandHandler implements ICommandHandler {
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

        Robot robot = new Robot(asteroid, name);
        Logger.Log(InputOutputController.GetInstance(), "CREATE " + name);
        Logger.Return();

        return true;
    }
}
