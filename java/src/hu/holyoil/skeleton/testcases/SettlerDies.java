package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.storage.PlayerStorage;

/**
 * Teszteli amikor egy telepes meghal
 * Dokumentumban: 11. oldalon látható a SZEKVENCIA diagram,
 *                         20. és 21. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazás: Van a telepes tárolójában legalább egy teleporter? (Ha van, fel kell robbantani a párjával együtt.)
 */
public class SettlerDies extends TestCase {
    private Settler s;

    @Override
    public String Name() {
        return "Settler dies";
    }

    @Override
    protected void load() {
        Asteroid a=new Asteroid("onAsteroid");
        s=new Settler(a, "s", "storage");
        PlayerStorage ps= s.GetStorage();

        Logger.RegisterObject(this, "TestFixture");
        boolean hasTeleporter = Logger.GetBoolean(this, "Does the Settler have one teleporter?");

        if(hasTeleporter){
            TeleportGate tp=new TeleportGate("t");
            TeleportGate pair=new TeleportGate("pair");
            pair.SetPair(tp);
            tp.SetPair(pair);

            ps.AddTeleportGatePair(tp, pair);
        }
        GameController.GetInstance().AddAsteroid(a);
        GameController.GetInstance().AddSettler(s);
    }

    @Override
    protected void start() {
        s.Die();
    }
}
