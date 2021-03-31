package hu.holyoil.skeleton.testcases;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Water;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Teszteli amikor egy vízjeget tartalmazó aszteroida napközelbe kerül és a jég szublimál.
 * Dokumentumban: 18. oldalon látható a SZEKVENCIA diagram,
 *                          25. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazás: 0 vagy több réteg van még hátra a magig? (A jég csak akkor szublimál ha ki van fúrva teljesen.)
 *           Napközelben van az aszteroida?
 */
public class WaterEvaporates extends TestCase {
    private Asteroid a;
    private Water resource;

    @Override
    public String Name() {
        return "Water evaporates";
    }

    @Override
    protected void load() {
        a = new Asteroid("a");
        resource = new Water("resource");

        Logger.RegisterObject(this,"TestFixture");

        Boolean isNearSun = Logger.GetBoolean(this, "Is the Asteroid near the Sun?");
        a.SetIsNearbySun(isNearSun);

        int numOfLayersRemaining = Logger.GetInteger(this, "How many layers does this Asteroid have left?");
        a.SetNumOfLayersRemaining(numOfLayersRemaining);

        a.SetResource(resource);
    }

    @Override
    protected void start() {
        a.ReactToSunNearby();
    }
}
