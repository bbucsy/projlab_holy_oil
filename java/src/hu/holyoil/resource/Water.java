package hu.holyoil.resource;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;

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
        //Logger.Log(this,"Being compared to " + Logger.GetName(abstractBaseResource));
        //Logger.Return();
        return abstractBaseResource instanceof Water;
    }

    @Override
    public void ReactToSunNearby(Asteroid asteroid) {
        Logger.Log(this,"Reacting to Sun nearby");
        asteroid.SetResource(null);
        Logger.Return();
    }
}
