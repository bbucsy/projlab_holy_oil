package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli a telepes mozgását egyik aszteroidáról a másikra.
 * Dokumentumban: 12. oldalon látható a SZEKVENCIA diagram,
 *                           22. és 23. oldalon a KOMMUNIKÁCIÓS diagram
 */
public class SettlerMovesToAsteroid extends TestCase {

    private Settler s;
    private Asteroid target;

    @Override
    public String Name() {
        return "Settler moves to Asteroid";
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
