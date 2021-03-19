package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class AsteroidExplodesWithTeleportGate extends TestCase {

    Asteroid asteroid;

    @Override
    public String Name() {
        return "Asteroid explodes with teleportgate on it";
    }

    @Override
    protected void load() {

        Logger.SetEnabled(false);

        Logger.RegisterObject(this, "TestFixture");
        asteroid = new Asteroid();
        Logger.RegisterObject(asteroid, "a: Asteroid");
        Logger.RegisterObject(GameController.getInstance(), ": GameController");
        Logger.RegisterObject(SunController.getInstance(), ": SunController");

        boolean isTeleportgateInStorage = Logger.GetBoolean(this, "Is the teleportgate in storage?");

        TeleportGate t1 = new TeleportGate(), t2 = new TeleportGate();
        Logger.RegisterObject(t1, "t1: TeleportGate");
        Logger.RegisterObject(t2, "t2: TeleportGate");
        t1.SetPair(t2);
        t2.SetPair(t1);

        Settler settler = new Settler(asteroid);
        Logger.RegisterObject(settler, "s: Settler");
        Logger.RegisterObject(settler.GetStorage(), "storage: PlayerStorage");

        settler.GetStorage().AddTeleportGatePair(
                t1, t2
        );

        settler.PlaceTeleporter();

        if (!isTeleportgateInStorage) {

            Asteroid otherAsteroid = new Asteroid();
            Logger.RegisterObject(otherAsteroid, "otherAsteroid: Asteroid");
            t2.SetHomeAsteroid(otherAsteroid);

            otherAsteroid.SetTeleporter(settler.GetStorage().GetOneTeleporter());
            settler.GetStorage().RemoveTeleportGate(
                    settler.GetStorage().GetOneTeleporter()
            );

        }

        Logger.SetEnabled(true);

    }

    @Override
    protected void start() {

        asteroid.Explode();

    }
}
