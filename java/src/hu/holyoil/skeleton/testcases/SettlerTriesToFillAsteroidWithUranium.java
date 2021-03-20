package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.storage.PlayerStorage;

public class SettlerTriesToFillAsteroidWithUranium extends TestCase {
    private PlayerStorage ps;
    private Uranium u;
    private Uranium u1;
    private Asteroid a;
    private Settler s;

    @Override
    public String Name() {
        return "Settler tries to fill asteroid with uranium";
    }

    @Override
    protected void load() {
        u = new Uranium();
        u1 = new Uranium();
        a = new Asteroid();
        s = new Settler(a);
        ps = s.GetStorage();

        Logger.RegisterObject(ps, "ps: PlayerStorage");
        Logger.RegisterObject(u, "u: Urnaium");
        Logger.RegisterObject(u1, "u1: Uranium");
        Logger.RegisterObject(s, "s: Settler");
        Logger.RegisterObject(a, "a: Asteroid");

        a.AddCrewmate(s);
        ps.SetStoredMaterial(u);
        a.SetResource(u1);

        Logger.RegisterObject(this, "TestFixture");
        int numOfLayersRemaining = Logger.GetInteger(this, "How many layers does this Asteroid have left?");
        a.SetNumOfLayersRemaining(numOfLayersRemaining);
        Logger.Return();
    }

    @Override
    protected void start() {
        a.PutResource(s, u);
    }
}
