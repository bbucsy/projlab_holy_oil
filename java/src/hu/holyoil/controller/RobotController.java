package hu.holyoil.controller;

import hu.holyoil.crewmate.Robot;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class RobotController implements ISteppable {

    private static RobotController robotController;
    private List<Robot> robots;

    @Override
    public void Step()  {
        System.out.println("Stepping");
    }

    public void AddRobot(Robot robot)  { System.out.println("Adding robot " + robot.toString()); }
    public void RemoveRobot(Robot robot)  { System.out.println("Removing robot " + robot.toString()); }
    public void HandleRobot(Robot robot)  { System.out.println("Handling robot " + robot.toString()); }

    public static RobotController getInstance() {

        if (robotController == null) {
            robotController = new RobotController();
        }

        return robotController;

    }

    private RobotController() {
        //robots = new ArrayList<>();
    }

}
