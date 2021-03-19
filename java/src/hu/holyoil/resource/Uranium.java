package hu.holyoil.resource;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;

public class Uranium extends AbstractBaseResource {


    public Uranium() {
    }



    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        Logger.Log(this,"Being compared to " + Logger.GetName(abstractBaseResource));
        Logger.Return();
        return true;
    }

    @Override
    public void ReactToSunNearby(Asteroid asteroid) {
        Logger.Log(this,"Reacting to Sun nearby");
        asteroid.Explode();
        Logger.Return();
    }
}
