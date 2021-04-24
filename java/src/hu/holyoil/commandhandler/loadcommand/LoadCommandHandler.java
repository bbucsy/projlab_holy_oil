package hu.holyoil.commandhandler.loadcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.controller.InputOutputController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Az `load file` parancs megvalósítása.
 */
public class LoadCommandHandler implements ICommandHandler {
    /**
     * A kért file-ra InputStream-et nyit, és az InputOutputControllernek átadja a parancsokat.
     *
     * @param command feldolgozandó parancs sora
     * @return feldolgozás sikeressége
     */
    @Override
    public boolean Handle(String command) {
        String[] commandParams = command.split(" ");

        if (commandParams.length < 2) {
            System.out.println("Invalid number of arguments");
            return false;
        }

        try {
            InputStream input = new FileInputStream(commandParams[1]);
            InputOutputController.GetInstance().ParseCommand(input);
        }
        catch (FileNotFoundException e) {
            System.out.println("Invalid path for file");
            return false;
        }

        return true;
    }
}
