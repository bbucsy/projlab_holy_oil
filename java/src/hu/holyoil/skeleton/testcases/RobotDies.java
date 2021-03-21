package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.RobotController;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy robot "meghal".
 * Dokumentumban: 11. oldalon látható a SZEKVENCIA diagram,
 *                       20. oldalon a KOMMUNIKÁCIÓS diagram
 */
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
        Logger.RegisterObject(RobotController.GetInstance(), "controller: RobotController");

        RobotController.GetInstance().AddRobot(robot);
    }

    @Override
    protected void start() {
        robot.Die();
    }
}
