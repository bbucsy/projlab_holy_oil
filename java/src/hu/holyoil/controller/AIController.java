package hu.holyoil.controller;

import hu.holyoil.crewmate.Robot;
import hu.holyoil.crewmate.Ufo;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;

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

        ufos.forEach(this::HandleUfo);
        robots.forEach(this::HandleRobot);
        teleporters.forEach(this::HandleTeleportGate);

    }

    /**
     * Hozzáad egy robotot a játékhoz.
     * <p>Ez azonnal megtörténik, amint egy telepes legyártja.</p>
     * @param robot Hozzáadja a robots tagváltozóhoz
     */
    public void AddRobot(Robot robot)  {
        robots.add(robot);
    }
    /**
     * Hozzáad egy ufót a játékhoz.
     * @param ufo Hozzáadja az ufos tagváltozóhoz
     */
    public void AddUfo(Ufo ufo)  {
        ufos.add(ufo);
    }
    /**
     * Hozzáad egy teleportert a játékhoz.
     * @param teleportGate Hozzáadja a teleporters tagváltozóhoz
     */
    public void AddTeleportGate(TeleportGate teleportGate)  {
        teleporters.add(teleportGate);
    }
    /**
     * Töröl egy robotot a játékból
     * @param robot törli a robotot a robots tagváltozóból
     */
    public void RemoveRobot(Robot robot)  {
        robots.remove(robot);
    }
    /**
     * Töröl egy ufút a játékból
     * @param ufo törli az ufót az ufos tagváltozóból
     */
    public void RemoveUfo(Ufo ufo)  {
        ufos.remove(ufo);
    }
    /**
     * Töröl egy teleportert a játékból
     * @param teleportGate törli a teleportert a teleporters tagváltozóból
     */
    public void RemoveTeleportGate(TeleportGate teleportGate)  {
        teleporters.remove(teleportGate);
    }
    /**
     * Kezeli egy robot működését
     * <p>
     *     Prioritások (lehetséges kilépések): <ol>
     *         <li>Napvhiarkor bújjon el</li>
     *         <li>Ha van mit fúrni és NEM egy napközeli, 1 rétegű aszteroidán áll: fúr</li>
     *         <li>Ha talál fúrható, NEM üres, NEM napközeli ÉS NEM 1 rétegű aszteroidát: oda mozog</li>
     *         <li>Ha talál fúrható aszteroidát, oda mozog. (Következő körben nem fogja kifúrni úgyse, ha napközeli 1 rétegű)</li>
     *         <li>Ha semmit nem talált, menjen át egy teleporteren</li>
     *         <li>Ha nincs teleporter se, lépjen random szomszédra</li>
     *     </ol>
     *     Nem számol a játékos mozgásával, feltételezi, hogy előbb lép az összes robot, mint a Nap.
     * </p>
     * @param robot az adott robot
     */
    public void HandleRobot(Robot robot)  {
        Asteroid current = robot.GetOnAsteroid();
        List<Asteroid> neighbouringAsteroids = robot.GetOnAsteroid().GetNeighbours();
        boolean tpAvailable = (current.GetTeleporter() != null && current.GetTeleporter().GetPair().GetHomeAsteroid()!=null);
        if (SunController.GetInstance().GetTurnsUntilStorm() < 2) {//sunstorm happens at the end of this or next turn
            if (current.GetResource() == null && current.GetLayerCount() < 2) {//if current asteroid is empty and can be drilled, stay
                robot.Drill(); //call Drill() even if it doesn't do anything to stay in place and simplicity
                return;
            } else {
                int i = 0;
                while (i < neighbouringAsteroids.size() && neighbouringAsteroids.get(i).GetResource() != null
                        && neighbouringAsteroids.get(i).GetLayerCount() > 1) //check for neighbour that is empty and could be drilled in time
                    i++;
                if (i < neighbouringAsteroids.size()) {
                    robot.Move(neighbouringAsteroids.get(i));
                    return;
                } else if (tpAvailable) {//if tp is available, use it, maybe it puts the robot on an empty drilled asteroid
                    robot.Move(current.GetTeleporter());
                    return;
                } else {//look for a neighbour with a teleporter. maybe that will take the robot to an empty asteroid
                    i = 0;
                    while (i < neighbouringAsteroids.size() && neighbouringAsteroids.get(i).GetTeleporter() != null)
                        i++;
                    if (i < neighbouringAsteroids.size()) {
                        robot.Move(neighbouringAsteroids.get(i));
                        return;
                    }
                }
            }
        }//if all fails continue and hope for best
        //don't drill if water or uranium would react to the Sun, otherwise drill if makes sense
        if (current.GetLayerCount() > 0 && !(current.GetIsNearbySun() && current.GetLayerCount() == 1
                && current.GetResource()!=null)) {
            robot.Drill();
            return;
        }
        Random random = new Random();
        Asteroid target;
        int chosenIndex = random.nextInt(neighbouringAsteroids.size());
        int start = chosenIndex;
        boolean listOver = false;
        boolean shouldMove = false;
        //does NOT account for player movement
        do {
            if (chosenIndex == neighbouringAsteroids.size() - 1) {
                chosenIndex = -1; //goes through the list but starts at a random index, goes from end to beginning here
            }
            chosenIndex++;

            target = neighbouringAsteroids.get(chosenIndex);
            if (target.GetLayerCount() > 0 && target.GetResource() != null
                    && !(target.GetIsNearbySun() && target.GetLayerCount() == 1)) {
                //look for neighbour that: has layers, isn't near Sun AND would possibly react, isn't empty
                shouldMove = true;
            }

            if (chosenIndex == start) {
                //if back at beginning, exit cycle
                listOver = true;
            }
        } while (!listOver && !shouldMove);
        start = chosenIndex;
        listOver = false;
        while (!listOver && !shouldMove) {//only enters if didn't find suitable neighbour

            if (chosenIndex == neighbouringAsteroids.size() - 1) {
                chosenIndex = -1;
            }
            chosenIndex++;

            target = neighbouringAsteroids.get(chosenIndex);
            if (target.GetLayerCount() > 0) {
                //find a drillable empty neighbour
                shouldMove = true;
            }

            if (chosenIndex == start) {
                listOver = true;
            }
        }
        if (shouldMove) {//found desirable neighbour
            robot.Move(neighbouringAsteroids.get(chosenIndex));
        } else {
            if (tpAvailable)//go through tp, hope for richer asteroids
                robot.Move(current.GetTeleporter());
            else//everything failed, all neighbours suck, doesn't matter where robot moves
                robot.Move(current.GetRandomNeighbour());
        }
    }
    /**
     * Kezeli egy ufo működését
     * @param ufo az adott ufó
     */
    public void HandleUfo(Ufo ufo)  {

        if(ufo.GetOnAsteroid().GetLayerCount() == 0 && ufo.GetOnAsteroid().GetResource() != null)
            ufo.Mine();
        else
            ufo.Move(ufo.GetOnAsteroid().GetRandomNeighbour());

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
        List<Asteroid> neighbouringAsteroids = teleportGate.GetHomeAsteroid().GetNeighbours();

        Random random = new Random();
        int chosenIndex = random.nextInt(neighbouringAsteroids.size());
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
        }
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
        teleporters = new ArrayList<>();
    }

    /**
     * Játék végén törli az összes AI-t.
     */
    public void ResetAI(){
        robots = new ArrayList<>();
        ufos = new ArrayList<>();
        teleporters = new ArrayList<>();
    }

}
