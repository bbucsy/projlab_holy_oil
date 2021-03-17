package hu.holyoil.controller;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class GameController implements ISteppable  {

    private static GameController gameController;
    private List<Settler> settlers;
    private List<Asteroid> asteroids;

    @Override
    public void Step() { System.out.println("Stepping"); }

    public void CheckWinCondition()  { System.out.println("Checking win condition"); }
    public void CheckLoseCondition()  { System.out.println("Checking lose condition"); }
    public void CheckGameCondition()  { System.out.println("Checking game condition"); }
    public void StartGame()  { System.out.println("Starting game"); }
    public void AddAsteroid(Asteroid asteroid)  { System.out.println("Adding asteroid " + asteroid.toString()); }
    public void RemoveAsteroid(Asteroid asteroid)  { System.out.println("Removing asteroid " + asteroid.toString()); }
    public void AddSettler(Settler settler)  { System.out.println("Adding settler " + settler.toString()); }
    public void RemoveSettler(Settler settler)  { System.out.println("Removing settler " + settler.toString()); }

    public static GameController getInstance() {
        if (gameController == null) {
            gameController = new GameController();
        }
        return gameController;
    }

    private GameController() {

    }

}
