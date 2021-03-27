package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Ufo;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Az UFO mozog egy aszteroidáról a másikra.
 */
public class UfoMoves extends TestCase {
    Ufo ufo;
    Asteroid target;
    @Override
    public String Name() {
        return "UFO moves to asteroid";
    }

    @Override
    protected void load() {
        Asteroid home = new Asteroid();
        ufo = new Ufo(home);
        target = new Asteroid();
        Logger.RegisterObject(ufo, "ufo: Ufo");
        Logger.RegisterObject(home, "home: Asteroid");
        Logger.RegisterObject(target, "target: Asteroid");

        home.AddNeighbourAsteroid(target);
        target.AddNeighbourAsteroid(home);

    }

    @Override
    protected void start() {
        ufo.Move(target);
    }
}
