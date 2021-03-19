package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

import java.util.Scanner;

public class SettlerPlacesTeleporter extends TestCase {

    Settler settler;
    Scanner scanner = new Scanner(System.in);

    @Override
    public String Name() {
        return "Settler places Teleporter";
    }

    @Override
    protected void load() {

        Logger.RegisterObject(this, "TestFixture");
        boolean isTeleporterHere = Logger.GetBoolean(this, "Is there a teleporter here?");

        boolean doesSettlerHaveTeleporter = Logger.GetBoolean(this, "Does this settler have a teleporter?");

        Asteroid asteroid = new Asteroid();
        Logger.RegisterObject(asteroid, "onAsteroid: Asteroid");
        settler = new Settler(asteroid);
        Logger.RegisterObject(settler, "s: Settler");
        Logger.RegisterObject(settler.GetStorage(), "storage: PlayerStorage");
        settler.SetOnAsteroid(asteroid);
        asteroid.AddCrewmate(settler);

        if (isTeleporterHere) {
            TeleportGate teleportGate = new TeleportGate();
            Logger.RegisterObject(teleportGate, "teleportGate: TeleportGate");
            asteroid.SetTeleporter(teleportGate);
            teleportGate.SetHomeAsteroid(asteroid);
            teleportGate.SetHomeStorage(null);
        }

        if (doesSettlerHaveTeleporter) {
            TeleportGate t1 = new TeleportGate();
            Logger.RegisterObject(t1, "t1: TeleportGate");
            TeleportGate t2 = new TeleportGate();
            Logger.RegisterObject(t2, "t2: TeleportGate");
            t1.SetPair(t2);
            t2.SetPair(t1);
            settler.GetStorage().AddTeleportGatePair(
                    t1, t2
            );
        }

    }

    @Override
    protected void start() {

        settler.PlaceTeleporter();

    }
}
