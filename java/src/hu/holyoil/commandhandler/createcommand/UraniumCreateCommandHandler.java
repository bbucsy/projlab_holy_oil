package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;
import hu.holyoil.repository.PlayerStorageBaseRepository;
import hu.holyoil.resource.Uranium;
import hu.holyoil.storage.PlayerStorage;

public class UraniumCreateCommandHandler implements ICommandHandler {
    @Override
    public void Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 4) {

            System.out.println("Invalid number of arguments");
            return;

        }

        String name = commandParams[2];

        Uranium uranium = new Uranium(name);

        Asteroid asteroid = AsteroidRepository.GetInstance().Get(commandParams[3]);

        PlayerStorage playerStorage = PlayerStorageBaseRepository.GetInstance().Get(commandParams[3]);

        if (asteroid == null && playerStorage == null) {

            System.out.println("No asteroid or playerstorage exists with name: " + commandParams[3]);
            return;

        }

        if (asteroid != null) {

            if (asteroid.GetResource() != null) {

                System.out.println("Asteroid already has resource");
                return;

            }

            asteroid.SetResource(uranium);

        }

        if (playerStorage != null) {

            if (playerStorage.GetSumResources() > 10) {

                System.out.println("Playerstorage already has 10 resources");
                return;

            }

            BillOfMaterial billOfMaterial = new BillOfMaterial();
            billOfMaterial.AddMaterial(uranium);

            playerStorage.AddBill(billOfMaterial);

        }

        if (commandParams.length > 4) {

            int health = Integer.parseInt(commandParams[4]);

            if (health < 0 || health > 3) {

                System.out.println("Health must be between 0 and 5");
                return;

            }

            uranium.SetHealth(health);

        }

    }
}
