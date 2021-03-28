package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.AIController;
import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.crewmate.Ufo;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class AsteroidExplodesWithEverything extends TestCase {
    Asteroid a;
    @Override
    public String Name() {
        return "A settler, a robot and a UFO walk on an asteroid. They explode.";
    }

    @Override
    protected void load() {
        a = new Asteroid();
        Settler s = new Settler(a);
        Ufo ufo = new Ufo(a);
        Robot r= new Robot(a);
        TeleportGate t1 = new TeleportGate();
        TeleportGate t2 = new TeleportGate();

        Logger.RegisterObject(a, "a: Asteroid");
        Logger.RegisterObject(s, "s: Settler");
        Logger.RegisterObject(r, "r: Robot");
        Logger.RegisterObject(ufo, "ufo: Ufo");
        Logger.RegisterObject(t1, "t1: TeleportGate");
        Logger.RegisterObject(t2, "t2: TeleportGate");
        Logger.RegisterObject(s.GetStorage(), "storage: PlayerStorage");
        Logger.RegisterObject(AIController.GetInstance(), ": AIController");
        Logger.RegisterObject(GameController.GetInstance(), ": GameController");
        Logger.RegisterObject(SunController.GetInstance(), ": SunController");
        Logger.RegisterObject(this, "TestFixture");

        t1.SetHomeAsteroid(a);
        a.SetTeleporter(t1);

        boolean tpAtPlayer = Logger.GetBoolean(this, "Is the pair of the teleporter on this asteroid in the storage of the settler?");

        if(tpAtPlayer){
            t2.SetHomeStorage(s.GetStorage());
            s.GetStorage().AddTeleportGatePair(t2, new TeleportGate());
        }
        else{
            Asteroid tpPairHome = new Asteroid();
            Logger.RegisterObject(tpPairHome, "pairHome: Asteroid");
            GameController.GetInstance().AddAsteroid(tpPairHome);
            SunController.GetInstance().AddAsteroid(tpPairHome);
            t2.SetHomeAsteroid(tpPairHome);
            tpPairHome.SetTeleporter(t2);

        }


        t1.SetPair(t2);
        t2.SetPair(t1);

        AIController.GetInstance().AddUfo(ufo);
        AIController.GetInstance().AddRobot(r);
        AIController.GetInstance().AddTeleportGate(t1);
        AIController.GetInstance().AddTeleportGate(t2);

        GameController.GetInstance().AddSettler(s);
        GameController.GetInstance().AddAsteroid(a);

        SunController.GetInstance().AddAsteroid(a);

        for(int i=0; i<6; i++){
            Asteroid neigh = new Asteroid();
            Logger.RegisterObject(neigh, "neigh" + i + ": Asteroid");
            a.AddNeighbourAsteroid(neigh);
            neigh.AddNeighbourAsteroid(a);
            GameController.GetInstance().AddAsteroid(neigh);
            SunController.GetInstance().AddAsteroid(neigh);
        }
    }

    @Override
    protected void start() {
        a.Explode();
    }
}
