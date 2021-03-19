package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class Example extends TestCase {

    private Settler s;

    @Override
    public String Name() {
        return "Settler Drills";
    }

    @Override
    protected void load() {
        Asteroid a = new Asteroid();
        s = new Settler(a);

        Logger.RegisterObject(s, "s: Settler");
        Logger.RegisterObject(a, "onAsteroid: Asteroid");

        s.SetOnAsteroid(a);
        a.AddCrewmate(s);
    }

    @Override
    protected void start() {
        s.Drill();
    }

}
