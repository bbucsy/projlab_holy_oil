package hu.holyoil.controller;

import hu.holyoil.Main;
import hu.holyoil.crewmate.Settler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class InputOutputController {

    private HashMap<String, Object> writableObjects;

    private static InputOutputController inputOutputController;

    public static InputOutputController GetInstance() {

        if (inputOutputController == null) {
            inputOutputController = new InputOutputController();
        }

        return inputOutputController;

    }

    InputOutputController() {

        writableObjects = new HashMap<>();

    }

    public void RegisterObject(Object object, String name) {

        writableObjects.put(name, object);

    }

    public void RemoveObject(String name) {

        writableObjects.remove(name);

    }

    public String GetRandomUnusedName(String prefix) {

        int i = 1;
        while (writableObjects.get(prefix + i) != null) {
            i++;
        }
        return prefix + i;

    }

    public Object GetObject(String name) {

        return writableObjects.get(name);

    }

    public void WriteState() {

        System.out.println("---STATE---");
        for (Object object : writableObjects.values()) {
            System.out.println("\t" + object.toString());
        }
        System.out.println("---END OF STATE---");

    }

    public void ResetObjects() {

        writableObjects.clear();

    }

    public void ParseCommand(InputStream inputStream) {

        Scanner scanner = new Scanner(inputStream);

        String line = scanner.nextLine();

        if (line.length() <= 0) {
            return;
        }

        String[] command = line.split(" ");
        switch (command[0]) {
            case "do": {
                if (command.length < 3) {
                    System.out.println("Not enough arguments");
                }
                if (!(GetObject(command[1]) instanceof Settler)) {
                    System.out.println("No Settler with name \"" + command[1] + "\" found");
                    return;
                }
                switch (command[2].toUpperCase()) {
                    case "LOOKAROUND": {
                        // todo: Check exactly what info he has access to
                        WriteState();
                        break;
                    }
                    case "MOVE": {
                        break;
                    }
                    case "DRILL": {
                        ((Settler)GetObject(command[1])).Drill();
                        break;
                    }
                    case "MINE": {
                        ((Settler)GetObject(command[1])).Mine();
                        break;
                    }
                    case "PLACERESOURCE": {
                        if (command.length < 4) {
                            System.out.println("Not enough arguments");
                            return;
                        }
                        // todo: this function
                        break;
                    }
                    case "CRAFT": {
                        if (command.length < 4) {
                            System.out.println("Not enough arguments");
                            return;
                        }
                        if (command[3].toUpperCase().equals("ROBOT")) {

                            ((Settler)GetObject(command[1])).CraftRobot();

                        } else {

                            if (command[3].toUpperCase().equals("TELEPORT")) {

                                ((Settler)GetObject(command[1])).CraftTeleportGate();

                            } else {

                                System.out.println("Cannot craft " + command[3]);

                            }

                        }
                    }
                    default: {
                        System.out.println("Unrecognized command " + command[2]);
                        break;
                    }
                }
                break;
            }
            case "create": {
                // todo: handle creation logic
                break;
            }
            case "addneighbour": {
                // todo: handle neighbour add logic
                break;
            }
            case "step": {
                SunController.GetInstance().Step();
                GameController.GetInstance().Step();
                AIController.GetInstance().Step();
                break;
            }
            case "cause_sunstorm": {
                // todo: Get id-s for sunstorm
                SunController.GetInstance().StartSunstorm();
                break;
            }
            case "explode_asteroid": {
                // todo: Get asteroid id to explode
                break;
            }
            case "disable_random": {
                Main.isRandomEnabled = false;
                break;
            }
            case "load": {
                // todo: handle input
                break;
            }
            case "state": {
                WriteState();
                break;
            }
            default: {
                System.out.println("Command " + line.split(" ")[0] + " not recognized");
                break;
            }
        }

    }

}
