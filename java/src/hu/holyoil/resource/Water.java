package hu.holyoil.resource;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;

public class Water extends AbstractBaseResource {
    @Override
    public void ReactToMine(Asteroid asteroid, IStorageCapable iStorageCapable) {
        System.out.println("I am water " + this.toString() + " and I am being mined by " + iStorageCapable.toString() + " on " + asteroid.toString());
    }

    @Override
    public void ReactToPlace(Asteroid asteroid, IStorageCapable iStorageCapable) {
        System.out.println("I am water " + this.toString() + " and I am being placed by " + iStorageCapable.toString() + " on " + asteroid.toString());
    }

    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        System.out.println("I am water " + this.toString() + " and I am being compared to " + abstractBaseResource.toString());
        return null;
    }

    @Override
    public void ReactToSunNearby(Asteroid asteroid) {
        System.out.println("I am water " + this.toString() + " and I am explosed to a sun. I will empty the core of " + asteroid.toString());
    }
}
