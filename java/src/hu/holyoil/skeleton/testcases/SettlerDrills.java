package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class SettlerDrills extends TestCase {

    private Settler s;

    @Override
    public String Name() {
        return "Settler drills";
    }

    @Override
    protected void load() {
        Asteroid a = new Asteroid();
        s = new Settler(a);

        Logger.RegisterObject(a,"onAsteroid: Asteroid");
        Logger.RegisterObject(s,"s: Settler");
    }

    @Override
    protected void start() {
        s.Drill();
    }
}
