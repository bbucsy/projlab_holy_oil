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

        Logger.RegisterObject(a,"a: Asteroid");
        Logger.RegisterObject(resource,"resource: Water");

        a.SetResource(resource);
    }

    @Override
    protected void start() {
        a.ReactToSunNearby();
    }
}
