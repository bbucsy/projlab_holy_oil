package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class SettlerTriesToDrillThenMine extends TestCase {

    Settler s;

    @Override
    public String Name() {
        return "Settler tries to drill then mine";
    }

    @Override
    protected void load() {

        Asteroid asteroid = new Asteroid();
        s = new Settler(asteroid);

        Logger.RegisterObject(asteroid, "onAsteroid: Asteroid");
        Logger.RegisterObject(s, "s: Settler");

        Uranium uranium = new Uranium();
        asteroid.SetResource(uranium);
        asteroid.SetNumOfLayersRemaining(1);

        Logger.RegisterObject(uranium, "uranium: Uranium");

    }

    @Override
    protected void start() {
        s.Drill();
        s.Mine();
    }
}
