package hu.holyoil.controller;

import hu.holyoil.crewmate.Robot;
import hu.holyoil.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A robotokat irányító kontroller. Implementálja az ISteppable interfacet, így a robotok minden körben végrehajtanak egy lépést. Singleton osztály.
 * <p>A PROJEKT EZEN FÁZISÁBAN MÉG NINCS KÉSZ, A TESZTELÉSHEZ NEM SZÜKSÉGES. Ennek megfelelően a dokumentáció is csak felszínes megértést nyújt, amennyi a tesztekhez kellhet.</p>
 */
public class RobotController implements ISteppable {
    /**
     * Singleton osztály statikus tagváltozója amin keresztül el lehet érni.
     */
    private static RobotController robotController;
    /**
     * A pályán található összes "élő" robot listája
     */
    private List<Robot> robots;

    /**
     * Minden robot végrehajt egy lépést
     * <p>Jelenleg nincs realizálva, teszteléshez nem szükséges.</p>
     */
    @Override
    public void Step()  {
        System.out.println("Stepping");
    }

    /**
     * Hozzáad egy robotot a játékhoz.
     * <p>Ez azonnal megtörténik, amint egy telepes legyártja.</p>
     * @param robot Hozzáadja a robots tagváltozóhoz
     */
    public void AddRobot(Robot robot)  {
        Logger.Log(this,"Adding robot <" +  Logger.GetName(robot)+ " >");
        robots.add(robot);
        Logger.Return();
    }

    /**
     * Töröl egy robotot a játékból
     * @param robot törli a robotot a robots tagváltozóból
     */
    public void RemoveRobot(Robot robot)  {
        Logger.Log(this,"Removing robot <" +  Logger.GetName(robot)+ " >");
        robots.remove(robot);
        Logger.Return();
    }

    /**
     * Kezeli egy robot működését
     * @param robot az adott robot
     */
    public void HandleRobot(Robot robot)  {
        Logger.Log(this,"Handle robot <" +  Logger.GetName(robot)+ " >");
        // todo
        Logger.Return();
    }

    /**
     * Singleton osztályra lehet vele hivatkozni
     * @return visszaad egy instance-et
     */
    public static RobotController GetInstance() {

        if (robotController == null) {
            robotController = new RobotController();
        }

        return robotController;

    }

    /**
     * Privát konstruktor.
     * Nem lehet kívülről meghívni, nem lehet példányosítani.
     */
    private RobotController() {
        robots = new ArrayList<>();
    }

}
