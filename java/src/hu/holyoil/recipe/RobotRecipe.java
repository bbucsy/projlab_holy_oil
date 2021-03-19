package hu.holyoil.recipe;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.controller.RobotController;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.*;
import hu.holyoil.storage.PlayerStorage;

public class RobotRecipe implements IRecipe {

    private static RobotRecipe robotRecipe;

    @Override
    public void Craft(IStorageCapable iStorageCapable, Asteroid asteroid) {
        System.out.println("A robot is crafted by " + iStorageCapable.toString() + " on " + asteroid.toString());
        BillOfMaterial billOfMaterial = new BillOfMaterial();
        billOfMaterial.AddMaterial(new Iron());
        billOfMaterial.AddMaterial(new Uranium());
        billOfMaterial.AddMaterial(new Coal());
        PlayerStorage storage = iStorageCapable.GetStorage();
        if (storage.HasEnoughOf(billOfMaterial)) {
            storage.RemoveBill(billOfMaterial);
            Robot robot = new Robot(asteroid);
            asteroid.AddCrewmate(robot);
            RobotController.getInstance().AddRobot(robot);
        }
    }

    public static RobotRecipe getInstance() {

        if (robotRecipe == null) {
            robotRecipe = new RobotRecipe();
        }

        return robotRecipe;

    }

    private RobotRecipe() {}

}
