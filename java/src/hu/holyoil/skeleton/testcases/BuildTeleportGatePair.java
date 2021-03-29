package hu.holyoil.skeleton.testcases;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.controller.AIController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.recipe.TeleporterRecipe;
import hu.holyoil.resource.*;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy telepes megpróbál egy teleportpárt építeni
 * Dokumentumban: 9. oldalon látható a SZEKVENCIA diagram,
 *                      19. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazások: Van-e elég nyersanyag a telepesnél?
 *             Van-e a telepes tárolójában már egy vagy kettő teleporter. (Csak akkor lehet építeni ha nincs nála egy se.)
 */
public class BuildTeleportGatePair extends TestCase {

    Settler settler;

    @Override
    public String Name() {
        return "Settler builds / tries to build TeleportGate";
    }

    @Override
    protected void load() {

        Asteroid asteroid = new Asteroid();
        settler = new Settler(asteroid);
        Logger.RegisterObject(settler, "s: Settler");
        Logger.RegisterObject(settler.GetStorage(), "storage: PlayerStorage");
        Logger.RegisterObject(this, "TestFixture");
        boolean settlerHasTeleporterAlready = Logger.GetBoolean(this, "Does this Settler already have a teleporter?");
        boolean settlerHasEnoughResources = Logger.GetBoolean(this, "Does this Settler have enough Resources to create a teleportgate pair?");

        if (settlerHasTeleporterAlready) {

            TeleportGate t1 = new TeleportGate(), t2 = new TeleportGate();
            settler.GetStorage().AddTeleportGatePair(t1, t2);

        }

        if (settlerHasEnoughResources) {

            BillOfMaterial billOfMaterial = new BillOfMaterial();
            Iron iron1 = new Iron();
            Logger.RegisterObject(iron1, "storageIron1: Iron");
            Iron iron2 = new Iron();
            Logger.RegisterObject(iron2, "storageIron2: Iron");
            Water water = new Water();
            Logger.RegisterObject(water, "storageWater: Water");
            Uranium uranium = new Uranium();
            Logger.RegisterObject(uranium, "storageUranium: Uranium");
            billOfMaterial.AddMaterial(iron1);
            billOfMaterial.AddMaterial(iron2);
            billOfMaterial.AddMaterial(water);
            billOfMaterial.AddMaterial(uranium);
            settler.GetStorage().AddBill(
                billOfMaterial
            );

        }

    }

    @Override
    protected void start() {

        settler.CraftTeleportGate();

    }
}
