package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.storage.PlayerStorage;

public class SettlerDies extends TestCase {

    private Asteroid a;
    private Settler s;

    @Override
    public String Name() {
        return "Settler dies";
    }

    @Override
    protected void load() {
        a=new Asteroid();
        s=new Settler(a);

        Logger.RegisterObject(this, "TestFixture");
        boolean hasTeleporter = Logger.GetBoolean(this, "Does the Settler have one or more teleporters?");
        Logger.RegisterObject(a, "onAsteroid: Asteroid");
        Logger.RegisterObject(s, "s: Settler");
        Logger.RegisterObject(GameController.getInstance(), "GameController");

        GameController.getInstance().AddAsteroid(a);
        GameController.getInstance().AddSettler(s);

        if(hasTeleporter){
            TeleportGate tp=new TeleportGate();
            PlayerStorage ps=new PlayerStorage();

            tp.SetHomeStorage(ps);
            ps.AddTeleportGatePair(tp, null);

            Logger.RegisterObject(tp, "t: TeleportGate");
            Logger.RegisterObject(ps, "storage: PlayerStorage");
        }

    }

    @Override
    protected void start() {
        s.Die();
    }
}
