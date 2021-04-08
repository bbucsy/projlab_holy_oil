package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy aszteroida felrobban és van rajta egy teleporter.
 * EZ A SZEKVENCIA DAIGRAM HELYESEN CSAK A 6. BEADANDÓ DOKUMENTUMBAN SZEREPEL.
 * Dokumentumban: 17. oldalon látható a SZEKVENCIA diagram,
 *                     26. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazás: A felrobbanó aszteroidán található teleporter párja le van rakva egy aszteroidára, vagy még egy játékos tárolójában van? (Honnan kell eltávolítani a párt.)
 */
public class AsteroidExplodesWithTeleportGate extends TestCase {

    Asteroid asteroid;

    @Override
    public String Name() {
        return "Asteroid explodes with a TeleportGate on it";
    }

    @Override
    protected void load() {
        Logger.RegisterObject(this, "TestFixture");

        asteroid = new Asteroid("a");

        boolean isTeleportGateInStorage = Logger.GetBoolean(this, "Is the TeleportGate's pair in a settler's storage?");

        TeleportGate t1 = new TeleportGate("t1"), t2 = new TeleportGate("t2");
        t1.SetPair(t2);
        t2.SetPair(t1);

        Asteroid asteroid2 = new Asteroid("a2");
        asteroid.AddNeighbourAsteroid(asteroid2);
        asteroid2.AddNeighbourAsteroid(asteroid);

        Settler settler = new Settler(asteroid, "s", "storage");

        settler.GetStorage().AddTeleportGatePair(
                t1, t2
        );

        settler.PlaceTeleporter();
        settler.Move(asteroid2);

        if (!isTeleportGateInStorage) {
            settler.PlaceTeleporter();
        }
    }

    @Override
    protected void start() {
        asteroid.Explode();
    }
}
