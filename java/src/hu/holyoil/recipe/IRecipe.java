package hu.holyoil.recipe;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;

public interface IRecipe {

    void Craft(IStorageCapable iStorageCapable, Asteroid asteroid);

}
