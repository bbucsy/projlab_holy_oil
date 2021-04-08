package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.storage.PlayerStorage;

/**
 * Teszteli amikor egy telepes megpróbál egy üres aszteroidából bányászni.
 * Dokumentumban: 10. oldalon látható a SZEKVENCIA diagram,
 *                           25. oldalon a KOMMUNIKÁCIÓS diagram
 * Elágazás: 0 vagy több réteg van még hátra az aszteroida magjáig? (Belső működés miatt muszáj tudni van-e még rétege az aszteroidának, ez nincs loggolva.)
 */
public class SettlerMinesEmptyAsteroid extends TestCase {
    private Asteroid a;
    private Settler s;
    private PlayerStorage ps;

    @Override
    public String Name() {
        return "Settler tries to mine empty Asteroid";
    }

    @Override
    protected void load() {
        a = new Asteroid("a");
        s = new Settler(a, "s", "ps");
        ps = s.GetStorage();

        a.AddSpaceship(s);

        Logger.RegisterObject(this, "TestFixture");
        int numOfLayersRemaining = Logger.GetInteger(this, "How many layers does this Asteroid have left?");
        a.SetNumOfLayersRemaining(numOfLayersRemaining);
    }

    @Override
    protected void start() {
        s.Mine();
    }

}
