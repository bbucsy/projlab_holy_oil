package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.storage.PlayerStorage;

public class SettlerDies extends TestCase {
    private Settler s;

    @Override
    public String Name() {
        return "Settler dies";
    }

    @Override
    protected void load() {
        Asteroid a=new Asteroid();
        s=new Settler(a);
        PlayerStorage ps= s.GetStorage();

        Logger.RegisterObject(this, "TestFixture");
        boolean hasTeleporter = Logger.GetBoolean(this, "Does the Settler have one teleporter?");

        if(hasTeleporter){
            TeleportGate tp=new TeleportGate();
            TeleportGate pair=new TeleportGate();
            pair.SetPair(tp);
            tp.SetPair(pair);

            ps.AddTeleportGatePair(tp, pair);

            Logger.RegisterObject(tp, "t: TeleportGate");
            Logger.RegisterObject(pair, "pair: TeleportGate");
        }
        GameController.GetInstance().AddAsteroid(a);
        GameController.GetInstance().AddSettler(s);

        Logger.RegisterObject(a, "onAsteroid: Asteroid");
        Logger.RegisterObject(s, "s: Settler");
        Logger.RegisterObject(ps, "storage: PlayerStorage");
        Logger.RegisterObject(GameController.GetInstance(), "GameController");
    }

    @Override
    protected void start() {
        s.Die();
    }
}
