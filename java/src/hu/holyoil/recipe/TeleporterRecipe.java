package hu.holyoil.recipe;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.controller.RobotController;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.resource.*;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.storage.PlayerStorage;

public class TeleporterRecipe implements IRecipe {

    private static TeleporterRecipe teleporterRecipe;

    @Override
    public void Craft(IStorageCapable iStorageCapable, Asteroid asteroid) {

        Logger.Log(this, "Crafting teleportgate pair");
        Logger.Return();

        BillOfMaterial billOfMaterial = new BillOfMaterial();
        Iron iron1 = new Iron();
        Iron iron2 = new Iron();
        Uranium uranium = new Uranium();
        Water water = new Water();

        Logger.RegisterObject(billOfMaterial, "bill: BillOfMaterial");
        Logger.RegisterObject(iron1, "iron1: Iron");
        Logger.RegisterObject(iron2, "iron2: Iron");

        billOfMaterial.AddMaterial(iron1);
        billOfMaterial.AddMaterial(iron2);
        billOfMaterial.AddMaterial(uranium);
        billOfMaterial.AddMaterial(water);
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
