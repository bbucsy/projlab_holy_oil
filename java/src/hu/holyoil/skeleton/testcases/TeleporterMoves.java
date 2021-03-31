package hu.holyoil.skeleton.testcases;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Megkergült teleporter mozog egy szomszédos aszteroidára.
 * Az aszteroidának 6 szomszédja van, minden másodiknak van teleportere, ezekre nem tud mozogni.
 * Elvárjuk, hogy egy random, teleporter nélküli szomszédra mozogjon.
 */
public class TeleporterMoves extends TestCase {
    TeleportGate tp;
    @Override
    public String Name() {
        return "Crazy Teleporter moves";
    }

    @Override
    protected void load() {
        tp = new TeleportGate("t");
        tp.ReactToSunstorm();
        Logger.RegisterObject(this, "TextFixture");

        Asteroid home = new Asteroid("home");

        Asteroid neigh = new Asteroid("neigh");
        if (Logger.GetBoolean(this, "Should new target have a teleporter?")) {
            neigh.SetTeleporter(new TeleportGate());
        }
        home.AddNeighbourAsteroid(neigh);

        tp.SetHomeAsteroid(home);
        home.SetTeleporter(tp);

    }

    @Override
    protected void start() {
        tp.Move(
                tp.GetHomeAsteroid().GetNeighbours().get(0)
        );
    }
}
