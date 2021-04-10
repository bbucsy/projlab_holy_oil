package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy aszteroida felrobban és van rajta robot.
 * EZ A SZEKVENCIA DAIGRAM HELYESEN CSAK A 6. BEADANDÓ DOKUMENTUMBAN SZEREPEL.
 * Dokumentumban: 15. és 16. oldalon látható a SZEKVENCIA diagram,
 *                  27. oldalon a KOMMUNIKÁCIÓS diagram
 * A változás a dokumentumhoz képest: A testernek feltett kérdések a teszeset lefutása elején kerülnek feltételre.
 * Elágazás: A robot vagy átmegy egy teleporteren, vagy egy szomszédos aszteroidára, tester input alapján
 */
public class AsteroidExplodesWithRobot extends TestCase {
    INeighbour neighbour;
    Robot robot;
    Asteroid asteroid;

    @Override
    public String Name() {
        return "Asteroid explodes with a Robot on it";
    }

    @Override
    protected void load() {
        Logger.RegisterObject(this, "TestFixture");
        Boolean hasTeleport = Logger.GetBoolean(this, "Should Robot move through a teleport?");

        asteroid = new Asteroid("a");

        robot = new Robot(asteroid, "r");

        if (hasTeleport) {
            TeleportGate pair1 = new TeleportGate("t1");

            TeleportGate pair2 = new TeleportGate("t2");

            Asteroid newHome = new Asteroid("neigh");

            pair1.SetPair(pair2);
            pair1.SetHomeAsteroid(asteroid);
            asteroid.SetTeleporter(pair1);

            pair2.SetPair(pair1);
            pair2.SetHomeAsteroid(newHome);

            neighbour = pair1;
        }
        else {
            Asteroid newHome = new Asteroid("neigh");
            asteroid.AddNeighbourAsteroid(newHome);
            newHome.AddNeighbourAsteroid(asteroid);

            neighbour = newHome;
        }
    }

    @Override
    protected void start() {
        asteroid.Explode();
    }
}
