package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy aszteroida felrobban és van rajta egy telepes.
 * EZ A SZEKVENCIA DAIGRAM HELYESEN CSAK A 6. BEADANDÓ DOKUMENTUMBAN SZEREPEL.
 * Dokumentumban: 16. oldalon látható a SZEKVENCIA diagram,
 *                    28. oldalon a KOMMUNIKÁCIÓS diagram
 */
public class AsteroidExplodesWithSettler extends TestCase {
    Asteroid asteroid;
    Settler settler;

    @Override
    public String Name() {
        return "Asteroid explodes with a Settler on it";
    }

    @Override
    protected void load() {
        asteroid = new Asteroid("a");

        settler = new Settler(asteroid, "s", "storage");

        Logger.RegisterObject(this, "TestFixture");
    }

    @Override
    protected void start() {
        asteroid.Explode();
    }
}
