package hu.holyoil.controller;

import hu.holyoil.IIdentifiable;
import hu.holyoil.Main;
import hu.holyoil.commandhandler.addneighbourcommand.AddNeighbourCommandHandler;
import hu.holyoil.commandhandler.createcommand.CreateCommandHandler;
import hu.holyoil.commandhandler.causesunstormcommand.CauseSunstormCommandHandler;
import hu.holyoil.commandhandler.docommand.DoCommandHandler;
import hu.holyoil.commandhandler.loadcommand.LoadCommandHandler;
import hu.holyoil.commandhandler.statecommand.StateCommandHandler;
import hu.holyoil.commandhandler.explodeasteroidcommand.ExplodeAsteroidCommandHandler;
import hu.holyoil.repository.NeighbourBaseRepository;
import hu.holyoil.repository.PlayerStorageBaseRepository;
import hu.holyoil.repository.ResourceBaseRepository;
import hu.holyoil.repository.SpaceshipBaseRepository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class InputOutputController {

    private static InputOutputController inputOutputController;

    public static InputOutputController GetInstance() {
        if (inputOutputController == null) {
            inputOutputController = new InputOutputController();
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
                    break;
                }
                case "state": {
                    isRunning = new StateCommandHandler().Handle(line);
                    break;
                }
                case "exit": {
                    System.out.println("Closing application");
                    isRunning = false;
                    break;
                }
                default: {
                    System.out.println("Command not recognized: " + line.split(" ")[0]);
                    System.out.println("Closing application");
                    isRunning = false;
                    break;
                }
            }
        }
    }
}
