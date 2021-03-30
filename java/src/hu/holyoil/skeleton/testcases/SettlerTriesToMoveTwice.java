package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class SettlerTriesToMoveTwice extends TestCase {

    Settler s;
    Asteroid a1;
    Asteroid a2;

    @Override
    public String Name() {
        return "Settler tries to move twice";
    }

    @Override
    protected void load() {

        Logger.RegisterObject(this, "TestFixture");

        a1 = new Asteroid();
        a2 = new Asteroid();
        a1.AddNeighbourAsteroid(a2);
        a2.AddNeighbourAsteroid(a1);

        s = new Settler(a1);
        Logger.RegisterObject(a1, "a1: Asteroid");
        Logger.RegisterObject(a2, "a2: Asteroid");
        Logger.RegisterObject(s, "s: Settler");

    }

    @Override
    protected void start() {

        s.Move(a2);
        s.Move(a1);

    }
}
