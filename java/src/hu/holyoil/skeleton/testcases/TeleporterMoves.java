package hu.holyoil.skeleton.testcases;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class TeleporterMoves extends TestCase {
    TeleportGate tp;
    @Override
    public String Name() {
        return "Crazy Teleporter moves";
    }

    @Override
    protected void load() {
        tp = new TeleportGate();
        tp.ReactToSunstorm();
        Logger.RegisterObject(tp, "t: TeleportGate");

        Asteroid home = new Asteroid();
        Logger.RegisterObject(home, "home: Asteroid");

        for(int i=0; i<6; i++){
            Asteroid neigh = new Asteroid();
            if(i%2==0)
                neigh.SetTeleporter(new TeleportGate());
            home.AddNeighbourAsteroid(neigh);
            Logger.RegisterObject(neigh, "neigh" + i + ": Asteroid");
        }

        tp.SetHomeAsteroid(home);
        home.SetTeleporter(tp);

    }

    @Override
    protected void start() {
        tp.Move();
    }
}
