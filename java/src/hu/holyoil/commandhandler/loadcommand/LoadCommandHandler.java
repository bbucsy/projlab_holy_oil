package hu.holyoil.commandhandler.loadcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.controller.InputOutputController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LoadCommandHandler implements ICommandHandler {
    @Override
    public void Handle(String command) {
        String[] commandParams = command.split(" ");

        if (commandParams.length < 2) {
            System.out.println("Invalid number of arguments");
            return;
        }

        try {
            InputStream input = new FileInputStream(commandParams[1]);
            InputOutputController.GetInstance().ParseCommand(input);
        }
        catch (FileNotFoundException e) {
            System.out.println("Invalid path for file");
        }
    }
}
