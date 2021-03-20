package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

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

        asteroid = new Asteroid();
        Logger.RegisterObject(asteroid, "a: Asteroid");

        robot = new Robot(asteroid);
        Logger.RegisterObject(robot, "r: Robot");

        Logger.RegisterObject(SunController.getInstance(), ": SunController");
        Logger.RegisterObject(GameController.getInstance(), ": GameController");

        SunController.getInstance().AddAsteroid(asteroid);
        GameController.getInstance().AddAsteroid(asteroid);

        if (hasTeleport) {
            TeleportGate pair1 = new TeleportGate();
            Logger.RegisterObject(pair1, "t1: TeleportGate");

            TeleportGate pair2 = new TeleportGate();
            Logger.RegisterObject(pair2, "t2: TeleportGate");

            Asteroid newHome = new Asteroid();
            Logger.RegisterObject(newHome, "neigh: Asteroid");

            pair1.SetPair(pair2);
            pair1.SetHomeAsteroid(asteroid);
            asteroid.SetTeleporter(pair1);

            pair2.SetPair(pair1);
            pair2.SetHomeAsteroid(newHome);

            neighbour = pair1;
        }
        else {
            Asteroid newHome = new Asteroid();
            Logger.RegisterObject(newHome, "neigh: Asteroid");
            asteroid.AddNeighbourAsteroid(newHome);

            SunController.getInstance().AddAsteroid(newHome);
            GameController.getInstance().AddAsteroid(newHome);

            neighbour = newHome;
        }
    }

    @Override
    protected void start() {
        asteroid.Explode();
    }
}
