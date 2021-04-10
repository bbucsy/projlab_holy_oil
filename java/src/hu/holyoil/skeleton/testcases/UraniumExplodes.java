package hu.holyoil.skeleton.testcases;

import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy uránt tartalmazó aszteroida napközelbe kerül.
 * Dokumentumban: 15. oldalon látható a SZEKVENCIA diagram,
 *                          25. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazás: 0 vagy több réteg van még hátra a magig? (Az aszteroida csak akkor robban fel ha ki van fúrva teljesen.)
 *           Napközelben van az aszteroida?
             Hány élete van az uránnak?
 */
public class UraniumExplodes extends TestCase {

    Asteroid asteroid;

    @Override
    public String Name() {
        return "Uranium explodes";
    }

    @Override
    protected void load() {

        asteroid = new Asteroid("a");

        Uranium uranium = new Uranium("u");
        asteroid.SetResource(uranium);

        Logger.RegisterObject(this, "TestFixture");
        asteroid.SetNumOfLayersRemaining(Logger.GetInteger(this, "How many layers does this asteroid have?"));

        asteroid.SetIsNearbySun(Logger.GetBoolean(this, "Is this asteroid nearby sun?"));
        uranium.SetHealth(Logger.GetInteger(this,"How many health does the uranium have?"));
    }

    @Override
    protected void start() {

        asteroid.ReactToSunNearby();

    }
}
