package hu.holyoil.skeleton.testcases;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Water;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class WaterEvaporates extends TestCase {
    private Asteroid a;
    private Water resource;

    @Override
    public String Name() {
        return "Water Evaporates";
    }

    @Override
    protected void load() {
        a = new Asteroid();
        resource = new Water();

        Logger.RegisterObject(this,"TestFixture");
        Logger.RegisterObject(a,"a: Asteroid");
        Logger.RegisterObject(resource,"resource: Water");

        Boolean isNearSun = Logger.GetBoolean(this, "Is the Asteroid near the Sun?");
        a.SetIsNearbySun(isNearSun);
        Logger.Return();

        int numOfLayersRemaining = Logger.GetInteger(this, "How many layers does this Asteroid have left?");
        a.SetNumOfLayersRemaining(numOfLayersRemaining);
        Logger.Return();

        a.SetResource(resource);
    }

    @Override
    protected void start() {
        a.ReactToSunNearby();
    }
}
