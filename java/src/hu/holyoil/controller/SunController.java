package hu.holyoil.controller;

import hu.holyoil.neighbour.Asteroid;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

public class SunController implements ISteppable {

    private static SunController sunController;
    private Integer turnsUntilNextSunstorm;
    private List<Asteroid> asteroids;

    @Override
    public void Step() {
        System.out.println("Stepping");
    }

    public void AddAsteroid(Asteroid asteroid)  { System.out.println("Adding asteroid " + asteroid.toString()); }
    public void RemoveAsteroid(Asteroid asteroid)  { System.out.println("Removing asteroid " + asteroid.toString()); }
    public void StartSunstorm()  { System.out.println("Starting sunstorm"); }

    public static SunController getInstance() {

        if (sunController == null) {
            sunController = new SunController();
        }

        return sunController;

    }

    private SunController() {
        //turnsUntilNextSunstorm = 100;
        //asteroids = new ArrayList<>();
    }

}
