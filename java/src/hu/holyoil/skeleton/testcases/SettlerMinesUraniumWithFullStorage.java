package hu.holyoil.skeleton.testcases;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.storage.PlayerStorage;

import java.util.ArrayList;
import java.util.List;

public class SettlerMinesUraniumWithFullStorage extends TestCase {

    private Asteroid a;
    private Settler s;

    @Override
    public String Name() {
        return "Settler tries to mine uranium with full storage";
    }

    @Override
    protected void load() {
        Uranium u = new Uranium();
        a = new Asteroid();
        s = new Settler(a);
        PlayerStorage ps = s.GetStorage();

        Logger.RegisterObject(ps,"ps: PlayerStorage");
        Logger.RegisterObject(u,"u: Uranium");
        Logger.RegisterObject(s, "s: Settler");
        Logger.RegisterObject(a, "a: Asteroid");

        a.AddCrewmate(s);
        a.SetResource(u);

        BillOfMaterial bill = new BillOfMaterial();

        for (int i = 0; i < 10; i++) {
            Uranium addUranium = new Uranium();
            bill.AddMaterial(addUranium);
            Logger.RegisterObject(addUranium,"u"+i+": Uranium");
        }

        ps.AddBill(bill);

        Logger.RegisterObject(this, "TestFixture");
        int numOfLayersRemaining = Logger.GetInteger(this, "How many layers does this Asteroid have left?");
        a.SetNumOfLayersRemaining(numOfLayersRemaining);
    }

    @Override
    protected void start() {
        a.ReactToMineBy(s);
    }

}