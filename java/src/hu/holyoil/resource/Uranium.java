package hu.holyoil.resource;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;

public class Uranium extends AbstractBaseResource {
    @Override
    public void ReactToMine(Asteroid asteroid, IStorageCapable iStorageCapable) {
        System.out.println("I am uranium " + this.toString() + " and I am being mined by " + iStorageCapable.toString() + " on " + asteroid.toString());
    }

    @Override
    public void ReactToPlace(Asteroid asteroid, IStorageCapable iStorageCapable) {
        System.out.println("I am uranium " + this.toString() + " and I am being placed by " + iStorageCapable.toString() + " on " + asteroid.toString());
    }

    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        System.out.println("I am uranium " + this.toString() + " and I am being compared to " + abstractBaseResource.toString());
        return null;
    }

    @Override
    public void ReactToSunNearby(Asteroid asteroid) {
        System.out.println("I am uranium " + this.toString() + " and I am explosed to a sun. I will blow up " + asteroid.toString());
    }
}
