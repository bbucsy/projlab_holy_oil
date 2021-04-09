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

    InputOutputController() {

    }

    public void ParseCommand(InputStream inputStream) {

        Scanner scanner = new Scanner(inputStream);

        boolean isRunning = true;

        while (scanner.hasNextLine() && isRunning) {
            String line = scanner.nextLine();

            if (line.length() <= 0) {
                continue;
            }
            case "create": {
                new CreateCommandHandler().Handle(line);
                break;
            }
            case "load": {
                new LoadCommandHandler().Handle(line);
                break;
            }
            case "add_neighbour": {
                new AddNeighbourCommandHandler().Handle(line);
                break;
            }
            case "step": {
                AIController.GetInstance().Step();
                SunController.GetInstance().Step();
                GameController.GetInstance().Step();
                break;
            }
            case "cause_sunstorm": {
                new CauseSunstormCommandHandler().Handle(line);
                break;
            }
            case "explode_asteroid": {
                new ExplodeAsteroidCommandHandler().Handle(line);
                break;
            }
            case "disable_random": {
                Main.isRandomEnabled = false;
                break;
            }
            case "state": {
                new StateCommandHandler().Handle(line);
                break;
            }
            default: {
                System.out.println("Command not recognized: " + line.split(" ")[0]);
                break;
            }
        }
    }

}
