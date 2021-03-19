package hu.holyoil.skeleton.testcases;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.resource.Water;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class Example extends TestCase {

    private Settler s;
    private AbstractBaseResource r = new Water();
    private BillOfMaterial b = new BillOfMaterial();
    @Override
    public String Name() {
        return "Settler Drills";
    }

    @Override
    protected void load() {
        Asteroid a = new Asteroid();
        s = new Settler(a);


        Logger.RegisterObject(r,"w: Water");
        Logger.RegisterObject(b,"b: Bill");

        Logger.RegisterObject(s, "s: Settler");
        Logger.RegisterObject(a, "onAsteroid: Asteroid");

        s.SetOnAsteroid(a);
        a.AddCrewmate(s);
    }

    @Override
    protected void start() {
        b.AddMaterial(r);
    }

}
