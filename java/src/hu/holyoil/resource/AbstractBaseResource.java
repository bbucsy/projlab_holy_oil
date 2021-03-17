package hu.holyoil.resource;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;

public abstract class AbstractBaseResource {

    public abstract void ReactToMine(Asteroid asteroid, IStorageCapable iStorageCapable);
    public abstract void ReactToPlace(Asteroid asteroid, IStorageCapable iStorageCapable);
    public abstract Boolean IsSameType(AbstractBaseResource abstractBaseResource);

    public void ReactToSunNearby(Asteroid asteroid) {
        System.out.println("I am resource " + this.toString() + " and I am now nearby a sun");
    }

}
