package hu.holyoil.controller;

import hu.holyoil.Main;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.crewmate.Ufo;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.commandhandler.Logger;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Logger.Log(this, "Steps");

        ufos.forEach(this::HandleUfo);
        robots.forEach(this::HandleRobot);
        teleporters.forEach(this::HandleTeleportGate);

        Logger.Return();
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

        Asteroid current = robot.GetOnAsteroid();
        List<Asteroid> neighbouringAsteroids = robot.GetOnAsteroid().GetNeighbours();
        boolean tpAvailable = current.GetTeleporter()!=null;
        //napvihar esetén
        if(SunController.GetInstance().GetTurnsUntilStorm()<2) {
            if (current.GetResource() == null && current.GetLayerCount() < 2) {
                robot.Drill(); //call Drill() even if it doesn't do anything to stay in place and simplicity
                Logger.Return();
                return;
            } else {
                int i = 0;
                while (i < neighbouringAsteroids.size() && neighbouringAsteroids.get(i).GetResource() != null
                        && neighbouringAsteroids.get(i).GetLayerCount() > 1)
                    i++;
                if (i < neighbouringAsteroids.size()) {
                    robot.Move(neighbouringAsteroids.get(i));
                    Logger.Return();
                    return;
                } else if (tpAvailable) {
                    robot.Move(current.GetTeleporter());
                    Logger.Return();
                    return;
                } else {
                    i = 0;
                    while (i < neighbouringAsteroids.size() && neighbouringAsteroids.get(i).GetTeleporter() != null)
                        i++;
                    if (i < neighbouringAsteroids.size()) {
                        robot.Move(neighbouringAsteroids.get(i));
                        Logger.Return();
                        return;
                    }
                }
            }
        }
        if(current.GetLayerCount()>0 && !(current.GetIsNearbySun() && current.GetLayerCount()==1)){
            robot.Drill();
            Logger.Return();
            return;
        }

        Asteroid target;
        int chosenIndex = new Random().nextInt(neighbouringAsteroids.size());
        int start = chosenIndex;
        boolean listOver=false;
        boolean shouldMove=false;
        //nem napviharkor
        //does NOT account for player movement
        do {
            if (chosenIndex == neighbouringAsteroids.size() - 1) {
                chosenIndex = -1;
            }
            chosenIndex++;

            target = neighbouringAsteroids.get(chosenIndex);
            if (target.GetLayerCount() > 0) {
                if (target.GetResource() != null) {
                    if (!(target.GetIsNearbySun() && target.GetLayerCount() == 1)) {
                        shouldMove = true;
                    }
                }
            }

            if (chosenIndex == start) {
                listOver = true;
            }
        } while (!listOver && !shouldMove);
        start = chosenIndex;
        listOver = false;
        while (!listOver && !shouldMove) {

            if (chosenIndex == neighbouringAsteroids.size() - 1) {
                chosenIndex = -1;
            }
            chosenIndex++;

            target = neighbouringAsteroids.get(chosenIndex);
            if (target.GetResource() == null && target.GetLayerCount() > 0) {
                shouldMove = true;
            }

            if (chosenIndex == start) {
                listOver = true;
            }
        }
        if (shouldMove) {
            robot.Move(neighbouringAsteroids.get(chosenIndex));
        } else {
            if(tpAvailable)
                robot.Move(current.GetTeleporter());
            else
                robot.Move(current.GetRandomNeighbour());
        }
        Logger.Return();

    }
    /**
     * Kezeli egy ufo működését
     * @param ufo az adott ufó
     */
    public void HandleUfo(Ufo ufo)  {
        Logger.Log(this,"Handle ufo <" +  Logger.GetName(ufo)+ ">");

        if(ufo.GetOnAsteroid().GetLayerCount() == 0 && ufo.GetOnAsteroid().GetResource() != null)
            ufo.Mine();
        else
            ufo.Move(ufo.GetOnAsteroid().GetRandomNeighbour());

        Logger.Return();
    }
    /**
     * Kezeli egy teleporter működését
     * <p>
     *     Tesztesetben ha ki van kapcsolva a randomizálás: az első teleporter nélküli szomszédra küldi a teleportert.
     * </p>
     * <p>
     *     Rendes működésben: random szomszédtól kedzve nézi sorban, van-e szomszédjuk.
     * </p>
     * <p>
     *     Mindkét esetben kiléphet mozgás nelkül, ha minden szomszédnak van aszteroidája.
     * </p>
     * @param teleportGate az adott teleporter
     */
    public void HandleTeleportGate(TeleportGate teleportGate)  {
        Logger.Log(this,"Handle teleporter <" +  Logger.GetName(teleportGate)+ ">");

        List<Asteroid> neighbouringAsteroids = teleportGate.GetHomeAsteroid().GetNeighbours();

        if(!Main.isRandomEnabled){
            int i=0;
            while(i<neighbouringAsteroids.size() && neighbouringAsteroids.get(i).GetTeleporter()!=null)
                i++;
            if(i<neighbouringAsteroids.size())
                teleportGate.Move(neighbouringAsteroids.get(i));

        }
        else {
            int chosenIndex = new Random().nextInt(neighbouringAsteroids.size());
            int start = chosenIndex;
            boolean canMove = true;
            while (canMove && neighbouringAsteroids.get(chosenIndex).GetTeleporter() != null) {
                if (chosenIndex == neighbouringAsteroids.size() - 1) {
                    chosenIndex = -1;
                }
                chosenIndex++;
                if (chosenIndex == start) {
                    canMove = false;
                }
            }
            if (canMove) {
                teleportGate.Move(neighbouringAsteroids.get(chosenIndex));
            } else {
                Logger.Log(this, "All neighbours already have a teleporter, cannot move");
                Logger.Return();
            }
        }

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
