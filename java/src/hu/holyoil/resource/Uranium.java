package hu.holyoil.resource;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;

public class Uranium extends AbstractBaseResource {

    private static Integer ID = 0;
    private Integer myID;

    public Uranium() {
        myID = ID;
        ID++;
    }

    @Override
    public String toString() {
        return "uranium " + myID.toString();
    }

    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        System.out.println("I am uranium " + this.toString() + " and I am being compared to " + abstractBaseResource.toString());
        return null;
    }

    @Override
    public void ReactToSunNearby(Asteroid asteroid) {
        System.out.println("I am uranium " + this.toString() + " and I am explosed to a sun. I will blow up " + asteroid.toString());
        asteroid.Explode();
    }
}
