package hu.holyoil.controller;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;

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

    public void AddAsteroid(Asteroid asteroid)  {
        Logger.Log(this,"Adding asteroid <" +  Logger.GetName(asteroid)+ " >");
        asteroids.add(asteroid);
        Logger.Return();
    }
    public void RemoveAsteroid(Asteroid asteroid)  {
        Logger.Log(this,"Removing asteroid <" +  Logger.GetName(asteroid)+ " >");
        asteroids.remove(asteroid);
        Logger.Return();
    }
    public void StartSunstorm()  {
       Logger.Log(this,"Starting sunstorm");
       asteroids.forEach(Asteroid::ReactToSunstorm);
       Logger.Return();
    }

    public static SunController GetInstance() {

        if (sunController == null) {
            sunController = new SunController();
        }

        return sunController;

    }

    private SunController() {
        turnsUntilNextSunstorm = 100;
        asteroids = new ArrayList<>();
    }

}
