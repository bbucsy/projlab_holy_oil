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
        Logger.RegisterObject(uranium, "uranium: Uranium");
        Logger.RegisterObject(water, "water: Water");

        Logger.Log(this, "Adding iron to " + Logger.GetName(billOfMaterial));
        billOfMaterial.AddMaterial(iron1);
        Logger.Return();

        Logger.Log(this, "Adding iron to " + Logger.GetName(billOfMaterial));
        billOfMaterial.AddMaterial(iron2);
        Logger.Return();

        Logger.Log(this, "Adding uranium to " + Logger.GetName(billOfMaterial));
        billOfMaterial.AddMaterial(uranium);
        Logger.Return();

        Logger.Log(this, "Adding water to " + Logger.GetName(billOfMaterial));
        billOfMaterial.AddMaterial(water);
        Logger.Return();

        Logger.Log(this, "Getting player storage");
        PlayerStorage storage = iStorageCapable.GetStorage();
        Logger.Return();

        if (storage.HasEnoughOf(billOfMaterial) && storage.GetOneTeleporter() == null) {

            Logger.Log(this, "Removing bill");
            storage.RemoveBill(billOfMaterial);
            Logger.Return();

            TeleportGate t1 = new TeleportGate(), t2 = new TeleportGate();
            Logger.RegisterObject(t1, "t1: TeleportGate");
            Logger.RegisterObject(t2, "t2: TeleportGate");

            Logger.Log(this, "Setting pair of " + Logger.GetName(t1));
            t1.SetPair(t2);
            Logger.Return();

            Logger.Log(this, "Setting pair of " + Logger.GetName(t2));
            t2.SetPair(t1);
            Logger.Return();

            Logger.Log(this, "Adding teleportgates to storage");
            storage.AddTeleportGatePair(t1, t2);
            Logger.Return();

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
