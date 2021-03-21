package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.storage.PlayerStorage;

public class SettlerMinesEmptyAsteroid extends TestCase {
    private Asteroid a;
    private Settler s;
    private PlayerStorage ps;

    @Override
    public String Name() {
        return "Settler tries to mine empty Asteroid";
    }

    @Override
    protected void load() {
        a = new Asteroid();
        s = new Settler(a);
        ps = s.GetStorage();

        Logger.RegisterObject(ps,"ps: PlayerStorage");
        Logger.RegisterObject(s, "s: Settler");
        Logger.RegisterObject(a, "a: Asteroid");

        a.AddCrewmate(s);

        Logger.RegisterObject(this, "TestFixture");
        int numOfLayersRemaining = Logger.GetInteger(this, "How many layers does this Asteroid have left?");
        a.SetNumOfLayersRemaining(numOfLayersRemaining);
    }

    @Override
    protected void start() {
        a.ReactToMineBy(s);
    }

}
