package hu.holyoil.controller;

import hu.holyoil.Main;
import hu.holyoil.commandhandler.Logger;
import hu.holyoil.commandhandler.addneighbourcommand.AddNeighbourCommandHandler;
import hu.holyoil.commandhandler.causesunstormcommand.CauseSunstormCommandHandler;
import hu.holyoil.commandhandler.createcommand.CreateCommandHandler;
import hu.holyoil.commandhandler.docommand.DoCommandHandler;
import hu.holyoil.commandhandler.explodeasteroidcommand.ExplodeAsteroidCommandHandler;
import hu.holyoil.commandhandler.loadcommand.LoadCommandHandler;
import hu.holyoil.commandhandler.statecommand.StateCommandHandler;

import java.io.InputStream;
import java.util.*;

public class InputOutputController {

    private static InputOutputController inputOutputController;
    private static List<String> commands = Arrays.asList("echo_off", "echo_on", "do",
            "create", "load", "add_neighbour",
            "step", "cause_sunstorm", "explode_asteroid", "disable_random", "state", "exit", "play");

    private static int Distance(String a, String b) {
        int[][] d = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i < a.length() + 1; i++) {
            for (int j = 0; j < b.length() + 1; j++) {
                if (i == 0) d[0][j] = j;
                else if (j == 0) d[i][0] = i;
                else
                    d[i][j] = Collections.min(Arrays.asList(
                            d[i - 1][j] + 1,
                            d[i][j - 1] + 1,
                            d[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1)));
            }
        }
        return d[a.length()][b.length()];
    }

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
        boolean isPlayMode = false;

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
                case "play": {
                    isPlayMode = true;
                    Logger.SetEnabled(false);
                    break;
                }
                default: {
                    if (isPlayMode) {
                        System.out.println("Command not recognised.");
                        System.out.println("For all available commands refer to the documentation");
                        String closest = Collections.min(commands, Comparator.comparingInt(s -> Distance(s, command[0])));
                        System.out.println("\t Did you mean: " + closest + " ?");
                    } else {
                        System.out.println("Command not recognized: " + line.split(" ")[0]);
                        isRunning = false;
                    }
                    break;
                }
            }
        }
    }
}
