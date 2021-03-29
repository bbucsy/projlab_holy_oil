package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.AIController;
import hu.holyoil.crewmate.Ufo;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy UFO meghal.
 */
public class UfoDies extends TestCase {
    Ufo ufo;
    @Override
    public String Name() {
        return "UFO dies";
    }

    @Override
    protected void load() {
        Asteroid a = new Asteroid();
        ufo = new Ufo(a);
        AIController.GetInstance().AddUfo(ufo);

        Logger.RegisterObject(a, "onAsteroid: Asteroid");
        Logger.RegisterObject(ufo, "ufo: Ufo");
    }

    @Override
    protected void start() {
        ufo.Die();
    }
}
