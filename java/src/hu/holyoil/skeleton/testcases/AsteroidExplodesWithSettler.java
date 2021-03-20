package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

public class AsteroidExplodesWithSettler extends TestCase {
    Asteroid asteroid;
    Settler settler;

    @Override
    public String Name() {
        return "Asteroid explodes with a Settler on it";
    }

    @Override
    protected void load() {
        asteroid = new Asteroid();
        Logger.RegisterObject(asteroid, "a: Asteroid");

        settler = new Settler(asteroid);
        Logger.RegisterObject(settler, "s: Settler");

        Logger.RegisterObject(this, "TestFixture");
        Logger.RegisterObject(SunController.GetInstance(), ": SunController");
        Logger.RegisterObject(GameController.GetInstance(), ": GameController");
        Logger.RegisterObject(settler.GetStorage(), "storage: PlayerStorage");

        SunController.GetInstance().AddAsteroid(asteroid);
        GameController.GetInstance().AddAsteroid(asteroid);
        GameController.GetInstance().AddSettler(settler);
    }

    @Override
    protected void start() {
        asteroid.Explode();
    }
}
