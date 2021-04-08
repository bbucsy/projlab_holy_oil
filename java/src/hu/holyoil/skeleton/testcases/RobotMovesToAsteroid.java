package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli a robot mozgását egyik aszteroidásól a másikra.
 * Dokumentumban: 13. oldalon látható a SZEKVENCIA diagram,
 *                         22. oldalon a KOMMUNIKÁCIÓS diagram
 * Megjegyzés: Mivel a sikertelen mozgás gyakorlatban nem történhet meg, nincs rá teszteset programozva
 */
public class RobotMovesToAsteroid extends TestCase {

    private Robot r;
    private Asteroid target;

    @Override
    public String Name() {
        return "Robot moves to Asteroid";
    }

    @Override
    protected void load() {
        Asteroid current = new Asteroid("current");
        r = new Robot(current, "r");
        target =new Asteroid("target");

        current.AddNeighbourAsteroid(target);
        target.AddNeighbourAsteroid(current);

    }

    @Override
    protected void start() {
        r.Move(target);
    }
}
