package hu.holyoil.controller;

import hu.holyoil.Main;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.crewmate.Ufo;
import hu.holyoil.neighbour.TeleportGate;
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
     * Az pályán található összes teleporter.
     */
    private List<TeleportGate> teleporters;
    /**
     * Minden robot végrehajt egy lépést
     * <p>Jelenleg nincs realizálva, teszteléshez nem szükséges.</p>
     */
    @Override
    public void Step()  {
        Logger.Log(this, "Steps" + (Main.isRandomEnabled ? "" : " - switched off!"));

        if (Main.isRandomEnabled) {
            ufos.forEach(this::HandleUfo);
            robots.forEach(this::HandleRobot);
            teleporters.forEach(this::HandleTeleportGate);
        }
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
     * Hozzáad egy teleportert a játékhoz.
     * @param teleportGate Hozzáadja a teleporters tagváltozóhoz
     */
    public void AddTeleportGate(TeleportGate teleportGate)  {
        Logger.Log(this,"Adding teleporter <" +  Logger.GetName(teleportGate)+ ">");
        teleporters.add(teleportGate);
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
     * Töröl egy teleportert a játékból
     * @param teleportGate törli a teleportert a teleporters tagváltozóból
     */
    public void RemoveTeleportGate(TeleportGate teleportGate)  {
        Logger.Log(this,"Removing teleporter <" +  Logger.GetName(teleportGate)+ ">");
        teleporters.remove(teleportGate);
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
    /**
     * Kezeli egy teleporter működését
     * @param teleportGate az adott teleporter
     */
    public void HandleTeleportGate(TeleportGate teleportGate)  {
        Logger.Log(this,"Handle teleporter <" +  Logger.GetName(teleportGate)+ ">");
        // todo

        // nice idea for this logic
        /*int chosenIndex= new Random().nextInt(neighbouringAsteroids.size());
        int start = chosenIndex;
        boolean canMove = true;
        while(canMove && neighbouringAsteroids.get(chosenIndex).GetTeleporter()!=null){
            if(chosenIndex==neighbouringAsteroids.size()-1){
                chosenIndex=-1;
            }
            chosenIndex++;
            if(chosenIndex==start){
                canMove = false;
            }
        }
        if(canMove){
            neighbouringAsteroids.get(chosenIndex).SetTeleporter(teleportGate);
            teleportGate.SetHomeAsteroid(neighbouringAsteroids.get(chosenIndex));
            teleporter=null;
        }
        else {
            Logger.Log(this, "All neighbours already have a teleporter, cannot move");
            Logger.Return();
        }*/

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

        if (Logger.GetName(AIController) == null) {
            Logger.RegisterObject(AIController, ": AIController");
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
        teleporters = new ArrayList<>();
    }

}
