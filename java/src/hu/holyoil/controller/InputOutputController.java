package hu.holyoil.controller;

import hu.holyoil.IIdentifiable;
import hu.holyoil.Main;
import hu.holyoil.commandhandler.docommand.DoCommandHandler;
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

    public void WriteState() {

        System.out.println("---STATE---");

        ArrayList<IIdentifiable> toWrite = new ArrayList<>();

        toWrite.addAll(NeighbourBaseRepository.GetInstance().GetAll());
        toWrite.addAll(PlayerStorageBaseRepository.GetInstance().GetAll());
        toWrite.addAll(ResourceBaseRepository.GetInstance().GetAll());
        toWrite.addAll(SpaceshipBaseRepository.GetInstance().GetAll());
        toWrite.add(SunController.GetInstance());

        for (IIdentifiable iIdentifiable: toWrite) {
            System.out.println(iIdentifiable.toString());
        }

        System.out.println("---END OF STATE---");

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
                new DoCommandHandler().Handle(line);
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
                AIController.GetInstance().Step();
                SunController.GetInstance().Step();
                GameController.GetInstance().Step();
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