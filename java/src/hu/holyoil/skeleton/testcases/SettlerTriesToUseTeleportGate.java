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
        Asteroid onAsteroid = new Asteroid("onAsteroid");

        TeleportGate pair = new TeleportGate("pair");
        s = new Settler(onAsteroid, "s");
        gate = new TeleportGate("gate");

        gate.SetHomeAsteroid(onAsteroid);
        gate.SetPair(pair);
        pair.SetPair(gate);
        onAsteroid.SetTeleporter(gate);


    }

    @Override
    protected void start() {
        s.Move(gate);
    }
}
