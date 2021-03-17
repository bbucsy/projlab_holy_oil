package hu.holyoil.recipe;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;

public class RobotRecipe implements IRecipe {

    private static RobotRecipe robotRecipe;

    @Override
    public void Craft(IStorageCapable iStorageCapable, Asteroid asteroid) {
        System.out.println("A robot is crafted by " + iStorageCapable.toString() + " on " + asteroid.toString());
    }

    public static RobotRecipe getInstance() {

        if (robotRecipe == null) {
            robotRecipe = new RobotRecipe();
        }

        return robotRecipe;

    }

    private RobotRecipe() {}

}
