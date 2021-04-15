package hu.holyoil.controller;

import hu.holyoil.Main;
import hu.holyoil.commandhandler.addneighbourcommand.AddNeighbourCommandHandler;
import hu.holyoil.commandhandler.createcommand.CreateCommandHandler;
import hu.holyoil.commandhandler.causesunstormcommand.CauseSunstormCommandHandler;
import hu.holyoil.commandhandler.docommand.DoCommandHandler;
import hu.holyoil.commandhandler.loadcommand.LoadCommandHandler;
import hu.holyoil.commandhandler.statecommand.StateCommandHandler;
import hu.holyoil.commandhandler.explodeasteroidcommand.ExplodeAsteroidCommandHandler;
import hu.holyoil.commandhandler.Logger;

import java.io.InputStream;
import java.util.Scanner;

public class InputOutputController {

    private static InputOutputController inputOutputController;

    public static InputOutputController GetInstance() {
        if (inputOutputController == null) {
            inputOutputController = new InputOutputController();
        }

        if (Logger.GetName(inputOutputController) == null) {
            Logger.RegisterObject(inputOutputController, ": InputOutputController");
        }

        return inputOutputController;
    }

    public void ParseCommand(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        boolean isRunning = true;

        while (isRunning && scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.length() <= 0) {
                continue;
            }

            String[] command = line.split(" ");
            switch (command[0]) {
                case "echo_off": {
                    Logger.SetEnabled(false);
                    break;
                }
                case "echo_on": {
                    Logger.SetEnabled(true);
                    break;
                }
                case "do": {
                    isRunning = new DoCommandHandler().Handle(line);
                    break;
                }
                case "create": {
                    isRunning = new CreateCommandHandler().Handle(line);
                    break;
                }
                case "load": {
                    isRunning = new LoadCommandHandler().Handle(line);
                    break;
                }
                case "add_neighbour": {
                    isRunning = new AddNeighbourCommandHandler().Handle(line);
                    break;
                }
                case "step": {
                    AIController.GetInstance().Step();
                    SunController.GetInstance().Step();
                    GameController.GetInstance().Step();
                    break;
                }
                case "cause_sunstorm": {
                    isRunning = new CauseSunstormCommandHandler().Handle(line);
                    break;
                }
                case "explode_asteroid": {
                    isRunning = new ExplodeAsteroidCommandHandler().Handle(line);
                    break;
                }
                case "disable_random": {
                    Main.isRandomEnabled = false;
                    SunController.GetInstance().SetCountdown(30);
                    break;
                }
                case "state": {
                    boolean temp = Logger.IsEnabled();
                    Logger.SetEnabled(true);
                    isRunning = new StateCommandHandler().Handle(line);
                    Logger.SetEnabled(temp);
                    break;
                }
                case "exit": {
                    isRunning = false;
                    break;
                }
                default: {
                    System.out.println("Command not recognized: " + line.split(" ")[0]);
                    isRunning = false;
                    break;
                }
            }
        }
    }
}
