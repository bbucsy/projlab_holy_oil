package hu.holyoil.recipe;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.controller.RobotController;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.*;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.storage.PlayerStorage;

public class RobotRecipe implements IRecipe {

    private static RobotRecipe robotRecipe;

    @Override
    public void Craft(IStorageCapable iStorageCapable, Asteroid asteroid) {

        Logger.Log(this, "Crafting Robot");
        Logger.Return();

        BillOfMaterial billOfMaterial = new BillOfMaterial();
        Logger.RegisterObject(billOfMaterial, "bill: BillOfMaterial");

        Iron iron = new Iron();
        Logger.RegisterObject(iron, "iron: Iron");

        Uranium uranium = new Uranium();
        Logger.RegisterObject(uranium, "uranium: Uranium");

        Coal coal = new Coal();
        Logger.RegisterObject(coal, "coal: Coal");

        Logger.Log(this, "Adding iron to " + Logger.GetName(billOfMaterial));
        billOfMaterial.AddMaterial(iron);
        Logger.Return();

        Logger.Log(this, "Adding uranium to " + Logger.GetName(billOfMaterial));
        billOfMaterial.AddMaterial(uranium);
        Logger.Return();

        Logger.Log(this, "Adding coal to " + Logger.GetName(billOfMaterial));
        billOfMaterial.AddMaterial(coal);
        Logger.Return();

        Logger.Log(this, "Getting player storage");
        PlayerStorage storage = iStorageCapable.GetStorage();
        Logger.Return();

        if (storage.HasEnoughOf(billOfMaterial)) {

            Logger.Log(this, "Removing bill from storage");
            storage.RemoveBill(billOfMaterial);
            Logger.Return();

            Robot robot = new Robot(asteroid);
            Logger.RegisterObject(robot, "r: Robot");

            Logger.Log(this, "Adding " + Logger.GetName(robot) + " to asteroid " + Logger.GetName(asteroid));
            asteroid.AddCrewmate(robot);
            Logger.Return();

            Logger.Log(this, "Registering " + Logger.GetName(robot) + " at the robotcontroller");
            RobotController.GetInstance().AddRobot(robot);
            Logger.Return();

        }

    }

    public static RobotRecipe GetInstance() {

        if (robotRecipe == null) {
            robotRecipe = new RobotRecipe();
        }

        return robotRecipe;

    }

    private RobotRecipe() {}

}
