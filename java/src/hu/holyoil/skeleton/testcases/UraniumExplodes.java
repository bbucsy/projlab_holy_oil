package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.skeleton.TestFramework;

public class UraniumExplodes extends TestCase {

    Asteroid asteroid;

    @Override
    public String Name() {
        return "Uranium explodes";
    }

    @Override
    protected void load() {

        // magic
        asteroid = new Asteroid();
        Logger.RegisterObject(asteroid, "a: Asteroid");

        Uranium uranium = new Uranium();
        Logger.RegisterObject(uranium, "u: Uranium");
        asteroid.SetResource(uranium);

        Logger.RegisterObject(this, "TestFixture");
        asteroid.SetNumOfLayersRemaining(Logger.GetInteger(this, "How many layers does this asteroid have?"));
        Logger.RegisterObject(SunController.getInstance(), ": SunController");
        Logger.RegisterObject(GameController.getInstance(), ": GameController");

        asteroid.SetIsNearbySun(Logger.GetBoolean(this, "Is this asteroid nearby sun?"));

    }

    @Override
    protected void start() {

        asteroid.ReactToSunNearby();

    }
}
