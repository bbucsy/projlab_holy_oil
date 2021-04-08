package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

import java.util.Scanner;

/**
 * Teszteli amikor egy telepes megpróbál lerakni egy teleportert egy aszteroidán.
 * Dokumentumban: 10. oldalon látható a SZEKVENCIA diagram,
 *                           19. és 20. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazás: Van az aszteroidán már teleporter? (Egy aszteroidán egyszerre egy teleporter lehet.)
 *           Van a telepesnél teleporter amit le tud rakni?
 */
public class SettlerPlacesTeleporter extends TestCase {

    Settler settler;

    @Override
    public String Name() {
        return "Settler places / tries to place TeleportGate";
    }

    @Override
    protected void load() {

        Logger.RegisterObject(this, "TestFixture");
        boolean isTeleporterHere = Logger.GetBoolean(this, "Is there a teleporter here?");

        boolean doesSettlerHaveTeleporter = Logger.GetBoolean(this, "Does this settler have a teleporter?");

        Asteroid asteroid = new Asteroid("onAsteroid");
        settler = new Settler(asteroid, "s", "storage");
        settler.SetOnAsteroid(asteroid);
        asteroid.AddSpaceship(settler);

        if (isTeleporterHere) {
            TeleportGate teleportGate = new TeleportGate("teleportGate");
            TeleportGate pairGate = new TeleportGate("nonExistentPair");
            teleportGate.SetPair(pairGate);
            pairGate.SetPair(teleportGate);
            pairGate.SetHomeStorage(settler.GetStorage());
            pairGate.SetHomeAsteroid(null);
            asteroid.SetTeleporter(teleportGate);
            teleportGate.SetHomeAsteroid(asteroid);
            teleportGate.SetHomeStorage(null);
        }

        if (doesSettlerHaveTeleporter) {
            TeleportGate t1 = new TeleportGate("t1");
            TeleportGate t2 = new TeleportGate("t2");
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
