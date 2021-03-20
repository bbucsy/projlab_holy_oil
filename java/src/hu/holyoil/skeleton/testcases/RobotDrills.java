package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Robot;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class RobotDrills extends TestCase {
    private Robot r;

    @Override
    public String Name() {
        return "Robot drills";
    }

    @Override
    protected void load() {
        Logger.RegisterObject(this, "TestFixture");

        int layers = Logger.GetInteger(this,"How many layers does the asteroid have?");
        Asteroid a = new Asteroid();
        a.SetNumOfLayersRemaining(layers);
        r = new Robot(a);


        Logger.RegisterObject(a,"onAsteroid: Asteroid");
        Logger.RegisterObject(r,"r: Robot");
    }

    @Override
    protected void start() {
        r.Drill();
    }
}
