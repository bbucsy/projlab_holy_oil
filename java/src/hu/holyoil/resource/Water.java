package hu.holyoil.resource;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;

public class Water extends AbstractBaseResource {

    private static Integer ID = 0;
    private Integer myID;

    public Water() {
        myID = ID;
        ID++;
    }

    @Override
    public String toString() {
        return "water " + myID.toString();
    }

    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        System.out.println("I am water " + this.toString() + " and I am being compared to " + abstractBaseResource.toString());
        return null;
    }

    @Override
    public void ReactToSunNearby(Asteroid asteroid) {
        System.out.println("I am water " + this.toString() + " and I am exposed to a sun. I will empty the core of " + asteroid.toString());
        asteroid.SetResource(null);
    }
}
