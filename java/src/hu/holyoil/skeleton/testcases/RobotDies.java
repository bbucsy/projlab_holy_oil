package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.RobotController;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class RobotDies extends TestCase {

    private Asteroid asteroid;
    private Robot robot;

    @Override
    public String Name() {
        return "Robot dies";
    }

    @Override
    protected void load() {
        asteroid = new Asteroid();
        robot = new Robot(asteroid);

        Logger.RegisterObject(asteroid, "onAsteroid: Asteroid");
        Logger.RegisterObject(robot, "r: Robot");
        Logger.RegisterObject(RobotController.getInstance(), "controller: RobotController");

        RobotController.getInstance().AddRobot(robot);
    }

    @Override
    protected void start() {
        robot.Die();
    }
}
