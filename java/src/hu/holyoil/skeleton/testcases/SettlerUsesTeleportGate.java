package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import sun.rmi.runtime.Log;

public class SettlerUsesTeleportGate extends TestCase {

    private TeleportGate gate;
    private Settler s;

    @Override
    public String Name() {
        return "Settler moves through teleport";
    }

    @Override
    protected void load() {
        Asteroid onAsteroid = new Asteroid();
        Asteroid targetAsteroid = new Asteroid();
        TeleportGate pair = new TeleportGate();
        s = new Settler(onAsteroid);
        gate = new TeleportGate();

        gate.SetHomeAsteroid(onAsteroid);
        gate.SetPair(pair);
        pair.SetHomeAsteroid(targetAsteroid);
        pair.SetPair(gate);
        targetAsteroid.SetTeleporter(pair);
        onAsteroid.SetTeleporter(gate);

        Logger.RegisterObject(onAsteroid,"onAsteroid: Asteroid");
        Logger.RegisterObject(targetAsteroid,"targetAsteroid: Asteroid");
        Logger.RegisterObject(pair,"pair: TeleportGate");
        Logger.RegisterObject(gate,"gate: TeleportGate");
        Logger.RegisterObject(s,"s: Settler");


    }

    @Override
    protected void start() {
        s.Move(gate);
    }
}
