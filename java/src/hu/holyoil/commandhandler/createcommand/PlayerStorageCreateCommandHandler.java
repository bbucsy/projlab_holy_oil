package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.repository.SettlerRepository;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.storage.PlayerStorage;

public class PlayerStorageCreateCommandHandler implements ICommandHandler {
    @Override
    public boolean Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 4) {

            System.out.println("Invalid number of arguments");
            return false;

        }

        String name = commandParams[2];

        Settler settler = SettlerRepository.GetInstance().Get(commandParams[3]);

        if (settler == null) {

            System.out.println("No settler exists with name: " + commandParams[3]);
            return false;

        }

        PlayerStorage playerStorage = new PlayerStorage(name);
        Logger.Log(InputOutputController.GetInstance(), "CREATE " + name);

        settler.SetStorage(playerStorage);

        Logger.Return();
        return true;
    }
}
