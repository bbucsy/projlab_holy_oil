package hu.holyoil.recipe;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;

public class TeleporterRecipe implements IRecipe {

    private static TeleporterRecipe teleporterRecipe;

    @Override
    public void Craft(IStorageCapable iStorageCapable, Asteroid asteroid) {
        System.out.println("A teleportgate is crafted by " + iStorageCapable.toString() + " on " + asteroid.toString());
    }

    public static TeleporterRecipe getInstance() {

        if (teleporterRecipe == null) {
            teleporterRecipe = new TeleporterRecipe();
        }

        return teleporterRecipe;

    }

    private TeleporterRecipe() {}

}
