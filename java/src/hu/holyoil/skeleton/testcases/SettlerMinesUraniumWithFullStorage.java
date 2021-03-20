package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.storage.PlayerStorage;

public class SettlerMinesUraniumWithFullStorage extends TestCase {
    private Uranium u;
    private Uranium u0,u1,u2,u3,u4,u5,u6,u7,u8,u9;
    private Asteroid a;
    private Settler s;
    private PlayerStorage ps;

    @Override
    public String Name() {
        return "Settler mines uranium with full storage";
    }

    @Override
    protected void load() {
        u = new Uranium();
        u0 = new Uranium();
        u1 = new Uranium();
        u2 = new Uranium();
        u3 = new Uranium();
        u4 = new Uranium();
        u5 = new Uranium();
        u6 = new Uranium();
        u7 = new Uranium();
        u8 = new Uranium();
        u9 = new Uranium();
        a = new Asteroid();
        s = new Settler(a);
        ps = s.GetStorage();

        Logger.RegisterObject(ps,"ps: PlayerStorage");
        Logger.RegisterObject(u,"u: Urnaium");
        Logger.RegisterObject(u0,"u0: Urnaium");
        Logger.RegisterObject(u1,"u1: Urnaium");
        Logger.RegisterObject(u2,"u2: Urnaium");
        Logger.RegisterObject(u3,"u3: Urnaium");
        Logger.RegisterObject(u4,"u4: Urnaium");
        Logger.RegisterObject(u5,"u5: Urnaium");
        Logger.RegisterObject(u6,"u6: Urnaium");
        Logger.RegisterObject(u7,"u7: Urnaium");
        Logger.RegisterObject(u8,"u8: Urnaium");
        Logger.RegisterObject(u9,"u9: Urnaium");
        Logger.RegisterObject(s, "s: Settler");
        Logger.RegisterObject(a, "a: Asteroid");

        a.AddCrewmate(s);
        a.SetResource(u);
        ps.SetStoredMaterial(u0);
        ps.SetStoredMaterial(u1);
        ps.SetStoredMaterial(u2);
        ps.SetStoredMaterial(u3);
        ps.SetStoredMaterial(u4);
        ps.SetStoredMaterial(u5);
        ps.SetStoredMaterial(u6);
        ps.SetStoredMaterial(u7);
        ps.SetStoredMaterial(u8);
        ps.SetStoredMaterial(u9);

        Logger.RegisterObject(this, "TestFixture");
        int numOfLayersRemaining = Logger.GetInteger(this, "How many layers does this Asteroid have left?");
        a.SetNumOfLayersRemaining(numOfLayersRemaining);
        Logger.Return();
    }

    @Override
    protected void start() {
        a.ReactToMineBy(s);
    }

}
