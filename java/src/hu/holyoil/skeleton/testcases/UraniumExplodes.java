package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.skeleton.TestFramework;

/**
 * Teszteli amikor egy uránt tartalmazó aszteroida napközelbe kerül.
 * Dokumentumban: 15. oldalon látható a SZEKVENCIA diagram,
 *                          25. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazás: 0 vagy több réteg van még hátra a magig? (Az aszteroida csak akkor robban fel ha ki van fúrva teljesen.)
 *           Napközelben van az aszteroida?
 */
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
        Logger.RegisterObject(SunController.GetInstance(), ": SunController");
        Logger.RegisterObject(GameController.GetInstance(), ": GameController");
        SunController.GetInstance().AddAsteroid(asteroid);
        GameController.GetInstance().AddAsteroid(asteroid);

        asteroid.SetIsNearbySun(Logger.GetBoolean(this, "Is this asteroid nearby sun?"));

    }

    @Override
    protected void start() {

        asteroid.ReactToSunNearby();

    }
}
