package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class SettlerMovesToAsteroid extends TestCase {

    private Settler s;
    private Asteroid target;

    @Override
    public String Name() {
        return "Settler moves to asteroid";
    }

    @Override
    protected void load() {
        Asteroid current = new Asteroid();
        target = new Asteroid();
        s = new Settler(current);

        target.AddNeighbourAsteroid(current);
        current.AddNeighbourAsteroid(target);

        Logger.RegisterObject(current,"current: Asteroid");
        Logger.RegisterObject(target, "target: Asteroid");
        Logger.RegisterObject(s,"s: Settler");

    }

    @Override
    protected void start() {
        s.Move(target);
    }
}
