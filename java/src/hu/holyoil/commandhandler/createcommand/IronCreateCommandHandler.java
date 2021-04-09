package hu.holyoil.commandhandler.createcommand;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;
import hu.holyoil.repository.PlayerStorageBaseRepository;
import hu.holyoil.resource.Iron;
import hu.holyoil.storage.PlayerStorage;

public class IronCreateCommandHandler implements ICommandHandler {
    @Override
    public void Handle(String command) {

        String[] commandParams = command.split(" ");

        if (commandParams.length < 4) {

            System.out.println("Invalid number of arguments");
            return;

        }

        String name = commandParams[2];

        Iron iron = new Iron(name);

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

            asteroid.SetResource(iron);

        }

        if (playerStorage != null) {

            if (playerStorage.GetSumResources() > 10) {

                System.out.println("Playerstorage already has 10 resources");
                return;

            }

            BillOfMaterial billOfMaterial = new BillOfMaterial();
            billOfMaterial.AddMaterial(iron);

            playerStorage.AddBill(billOfMaterial);

        }

    }
}