package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Robot;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy robot ásni próbál egy aszteroidán.
 * Dokumentumban: 12. oldalon látható a SZEKVENCIA diagram,
 *                        28. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazás: 0 vagy több réteg van még hátra az aszteroida magjáig?
 */
public class RobotDrills extends TestCase {
    private Robot r;

    @Override
    public String Name() {
        return "Robot drills";
    }

    @Override
    protected void load() {
        Logger.RegisterObject(this, "TestFixture");

        int layers = Logger.GetInteger(this,"How many layers does the asteroid have?");
        Asteroid a = new Asteroid("onAsteroid");
        a.SetNumOfLayersRemaining(layers);
        r = new Robot(a, "r");
    }

    @Override
    protected void start() {
        r.Drill();
    }
}
