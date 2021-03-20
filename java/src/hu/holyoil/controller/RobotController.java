package hu.holyoil.controller;

import hu.holyoil.crewmate.Robot;
import hu.holyoil.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;

public class RobotController implements ISteppable {

    private static RobotController robotController;
    private List<Robot> robots;

    @Override
    public void Step()  {
        System.out.println("Stepping");
    }

    public void AddRobot(Robot robot)  {
        Logger.Log(this,"Adding robot <" +  Logger.GetName(robot)+ " >");
        robots.add(robot);
        Logger.Return();
    }
    public void RemoveRobot(Robot robot)  {
        Logger.Log(this,"Removing robot <" +  Logger.GetName(robot)+ " >");
        robots.remove(robot);
        Logger.Return();
    }
    public void HandleRobot(Robot robot)  {
        Logger.Log(this,"Handle robot <" +  Logger.GetName(robot)+ " >");
        // todo
        Logger.Return();
    }

    public static RobotController GetInstance() {

        if (robotController == null) {
            robotController = new RobotController();
        }

        return robotController;

    }

    private RobotController() {
        robots = new ArrayList<>();
    }

}
