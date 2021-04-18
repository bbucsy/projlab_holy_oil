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
            "step", "cause_sunstorm", "explode_asteroid", "disable_random", "state", "exit", "play", "generate");

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
            boolean commandSuccess = true;

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
                    commandSuccess = new DoCommandHandler().Handle(line);
                    break;
                }
                case "create": {
                    commandSuccess = new CreateCommandHandler().Handle(line);
                    break;
                }
                case "load": {
                    commandSuccess = new LoadCommandHandler().Handle(line);
                    break;
                }
                case "add_neighbour": {
                    commandSuccess = new AddNeighbourCommandHandler().Handle(line);
                    break;
                }
                case "step": {
                    AIController.GetInstance().Step();
                    SunController.GetInstance().Step();
                    GameController.GetInstance().Step();
                    break;
                }
                case "cause_sunstorm": {
                    commandSuccess = new CauseSunstormCommandHandler().Handle(line);
                    break;
                }
                case "explode_asteroid": {
                    commandSuccess = new ExplodeAsteroidCommandHandler().Handle(line);
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
                    commandSuccess = new StateCommandHandler().Handle(line);
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
                case  "generate": {
                    boolean temp = Logger.IsEnabled();
                    Logger.SetEnabled(false);
                    GameController.GetInstance().StartGame();
                    Logger.SetEnabled(temp);
                    break;
                }
                default: {
                    commandSuccess = false;
                    break;
                }
            }
            //handle unsuccessful command
            if(!commandSuccess){
                if (isPlayMode) {
                    // play mode -> suggest similar available command
                    System.out.println("Command not recognised.");
                    String closest = Collections.min(commands, Comparator.comparingInt(s -> Distance(s, line)));
                    System.out.println("\t Did you mean: " + closest + " ?");
                } else {
                    // not in playmode -> exit with errror message.
                    System.out.println("Command not recognized: " + line.split(" ")[0]);
                    isRunning = false;
                }
            }
        }
    }
}
