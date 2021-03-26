package hu.holyoil.controller;

import hu.holyoil.crewmate.Robot;
import hu.holyoil.crewmate.Ufo;
import hu.holyoil.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Az AI-t irányító kontroller. Implementálja az ISteppable interfacet, így az irányított egységek minden körben végrehajtanak egy lépést. Singleton osztály.
 * <p>A PROJEKT EZEN FÁZISÁBAN MÉG NINCS KÉSZ, A TESZTELÉSHEZ NEM SZÜKSÉGES. Ennek megfelelően a dokumentáció is csak felszínes megértést nyújt, amennyi a tesztekhez kellhet.</p>
 */
public class AIController implements ISteppable {
    /**
     * Singleton osztály statikus tagváltozója amin keresztül el lehet érni.
     */
    private static AIController AIController;
    /**
     * A pályán található összes "élő" robot listája
     */
    private List<Robot> robots;
    /**
     * A pályán található összes "élő" UFO
     */
    private List<Ufo> ufos;

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
        Logger.Log(this,"Adding robot <" +  Logger.GetName(robot)+ ">");
        robots.add(robot);
        Logger.Return();
    }
    /**
     * Hozzáad egy ufót a játékhoz.
     * @param ufo Hozzáadja az ufos tagváltozóhoz
     */
    public void AddUfo(Ufo ufo)  {
        Logger.Log(this,"Adding ufo <" +  Logger.GetName(ufo)+ ">");
        ufos.add(ufo);
        Logger.Return();
    }

    /**
     * Töröl egy robotot a játékból
     * @param robot törli a robotot a robots tagváltozóból
     */
    public void RemoveRobot(Robot robot)  {
        Logger.Log(this,"Removing robot <" +  Logger.GetName(robot)+ ">");
        robots.remove(robot);
        Logger.Return();
    }
    /**
     * Töröl egy ufút a játékból
     * @param ufo törli az ufót az ufos tagváltozóból
     */
    public void RemoveUfo(Ufo ufo)  {
        Logger.Log(this,"Removing ufo <" +  Logger.GetName(ufo)+ ">");
        ufos.remove(ufo);
        Logger.Return();
    }
    /**
     * Kezeli egy robot működését
     * @param robot az adott robot
     */
    public void HandleRobot(Robot robot)  {
        Logger.Log(this,"Handle robot <" +  Logger.GetName(robot)+ ">");
        // todo
        Logger.Return();
    }
    /**
     * Kezeli egy ufo működését
     * @param ufo az adott ufó
     */
    public void HandleUfo(Ufo ufo)  {
        Logger.Log(this,"Handle ufo <" +  Logger.GetName(ufo)+ ">");
        // todo
        Logger.Return();
    }
    public void CreateUfos(){
        Logger.Log(this, "Creating UFOs at the beginning of the game");
        // todo
        Logger.Return();
    }
    /**
     * Singleton osztályra lehet vele hivatkozni
     * @return visszaad egy instance-et
     */
    public static AIController GetInstance() {

        if (AIController == null) {
            AIController = new AIController();
        }
        return AIController;
    }

    /**
     * Privát konstruktor.
     * Nem lehet kívülről meghívni, nem lehet példányosítani.
     */
    private AIController() {
        robots = new ArrayList<>();
        ufos = new ArrayList<>();
    }

}
