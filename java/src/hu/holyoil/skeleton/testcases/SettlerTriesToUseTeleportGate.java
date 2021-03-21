package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy telepes megpróbál használni egy teleport kaput aminek a párja nincs lerakva aszteroidára.
 * Dokumentumban: 14. oldalon látható a SZEKVENCIA diagram,
 *                          29. oldalon a KOMMUNIKÁCIÓS diagram
 */
public class SettlerTriesToUseTeleportGate extends TestCase {
    private TeleportGate gate;
    private Settler s;

    @Override
    public String Name() {
        return "Settler tries to use teleport gate without active pair";
    }

    @Override
    protected void load() {
        Asteroid onAsteroid = new Asteroid();

        TeleportGate pair = new TeleportGate();
        s = new Settler(onAsteroid);
        gate = new TeleportGate();

        gate.SetHomeAsteroid(onAsteroid);
        gate.SetPair(pair);
        pair.SetPair(gate);
        onAsteroid.SetTeleporter(gate);

        Logger.RegisterObject(onAsteroid,"onAsteroid: Asteroid");
        Logger.RegisterObject(pair,"pair: TeleportGate");
        Logger.RegisterObject(gate,"gate: TeleportGate");
        Logger.RegisterObject(s,"s: Settler");


    }

    @Override
    protected void start() {
        s.Move(gate);
    }
}
