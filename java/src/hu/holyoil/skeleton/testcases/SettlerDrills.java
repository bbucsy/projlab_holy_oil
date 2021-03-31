package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy telepes megpróbál ásni egy aszteroidán.
 * Dokumentumban: 11. oldalon látható a SZEKVENCIA diagram,
 *                          28. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazás: 0 vagy több réteg van még hátra az aszteroida magjáig?
 */
public class SettlerDrills extends TestCase {

    private Settler s;

    @Override
    public String Name() {
        return "Settler drills";
    }

    @Override
    protected void load() {
        Logger.RegisterObject(this, "TestFixture");

        int layers = Logger.GetInteger(this,"How many layers does the asteroid have?");
        Asteroid a = new Asteroid("onAsteroid");
        a.SetNumOfLayersRemaining(layers);
        s = new Settler(a, "s");
    }

    @Override
    protected void start() {
        s.Drill();
    }
}
