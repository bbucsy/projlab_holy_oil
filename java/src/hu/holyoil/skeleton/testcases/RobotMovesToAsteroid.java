package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class RobotMovesToAsteroid extends TestCase {

    private Robot r;
    private Asteroid target;

    @Override
    public String Name() {
        return "Robot moves to asteroid";
    }

    @Override
    protected void load() {
        Asteroid current = new Asteroid();
        r = new Robot(current);
        target =new Asteroid();

        current.AddNeighbourAsteroid(target);
        target.AddNeighbourAsteroid(current);

        Logger.RegisterObject(current,"current: Asteroid");
        Logger.RegisterObject(target,"target: Asteroid");
        Logger.RegisterObject(r,"r: Robot");

    }

    @Override
    protected void start() {
        r.Move(target);
    }
}
