package hu.holyoil.recipe;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.controller.RobotController;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.resource.*;
import hu.holyoil.storage.PlayerStorage;

public class TeleporterRecipe implements IRecipe {

    private static TeleporterRecipe teleporterRecipe;

    @Override
    public void Craft(IStorageCapable iStorageCapable, Asteroid asteroid) {
        System.out.println("A teleportgate is crafted by " + iStorageCapable.toString() + " on " + asteroid.toString());
        BillOfMaterial billOfMaterial = new BillOfMaterial();
        billOfMaterial.AddMaterial(new Iron());
        billOfMaterial.AddMaterial(new Iron());
        billOfMaterial.AddMaterial(new Uranium());
        billOfMaterial.AddMaterial(new Water());
        PlayerStorage storage = iStorageCapable.GetStorage();
        if (storage.HasEnoughOf(billOfMaterial) && storage.GetOneTeleporter() == null) {
            storage.RemoveBill(billOfMaterial);
            TeleportGate t1 = new TeleportGate(), t2 = new TeleportGate();
            t1.SetPair(t2);
            t2.SetPair(t1);
            storage.AddTeleportGatePair(t1, t2);
        }
    }

    public static TeleporterRecipe getInstance() {

        if (teleporterRecipe == null) {
            teleporterRecipe = new TeleporterRecipe();
        }

        return teleporterRecipe;

    }

    private TeleporterRecipe() {}

}
