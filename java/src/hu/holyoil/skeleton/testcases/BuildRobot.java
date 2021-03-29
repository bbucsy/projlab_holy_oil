package hu.holyoil.skeleton.testcases;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.controller.AIController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.recipe.RobotRecipe;
import hu.holyoil.resource.Coal;
import hu.holyoil.resource.Iron;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy telepes megpróbál felépíteni egy robotot.
 * Dokumentumban: 9. oldalon látható a SZEKVENCIA diagram,
 *                      21. és 22. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazás: Van-e elég nyersanyag egy robot építéséhez?
 */
public class BuildRobot extends TestCase {

    Settler settler;

    @Override
    public String Name() {
        return "Settler builds / tries to build Robot";
    }

    @Override
    protected void load() {

        Asteroid asteroid = new Asteroid();
        Logger.RegisterObject(this, "TestFixture");
        Logger.RegisterObject(asteroid, "a: Asteroid");
        settler = new Settler(asteroid);
        Logger.RegisterObject(settler, "s: Settler");
        Logger.RegisterObject(settler.GetStorage(), "storage: Storage");

        boolean canBuildRobot = Logger.GetBoolean(this, "Does the Settler have enough materials to build a robot?");

        if (canBuildRobot) {

            BillOfMaterial billOfMaterial = new BillOfMaterial();
            Iron iron = new Iron();
            Logger.RegisterObject(iron, "storageIron: Iron");
            Coal coal = new Coal();
            Logger.RegisterObject(coal, "storageCoal: Iron");
            Uranium uranium = new Uranium();
            Logger.RegisterObject(uranium, "storageUranium: Uranium");
            billOfMaterial.AddMaterial(iron);
            billOfMaterial.AddMaterial(coal);
            billOfMaterial.AddMaterial(uranium);
            settler.GetStorage().AddBill(
                    billOfMaterial
            );

        }

    }

    @Override
    protected void start() {

        settler.CraftRobot();

    }
}
