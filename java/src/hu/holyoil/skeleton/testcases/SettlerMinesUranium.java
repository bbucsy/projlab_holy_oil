package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;
import hu.holyoil.storage.PlayerStorage;

/**
 * Teszteli amikor egy telepes megpróbál kibányászni egy nyersanyagot.
 * <p>
 *     Mivel az AbstractResource nem példányosítható a tesztben Uránt használunk példaként.
 * </p>
 * Dokumentumban: 10. oldalon látható a SZEKVENCIA diagram,
 *                           23. oldalon a KOMMUNIKÁCIÓS diagram (ha az aszteroidának még van rétege)
 *                           és a 24. oldalon
 * Elágazás: 0 vagy több réteg van még hátra az aszteroida magjáig?
 */
public class SettlerMinesUranium extends TestCase {
    private Uranium u;
    private Asteroid a;
    private Settler s;
    private PlayerStorage ps;

    @Override
    public String Name() {
        return "Settler mines Uranium / Settler tries to mine Asteroid that still has layers";
    }

    @Override
    protected void load() {
        u = new Uranium("u");
        a = new Asteroid("a");
        s = new Settler(a, "s", "ps");
        ps = s.GetStorage();

        a.AddSpaceship(s);
        a.SetResource(u);

        Logger.RegisterObject(this, "TestFixture");
        int numOfLayersRemaining = Logger.GetInteger(this, "How many layers does this Asteroid have left?");
        a.SetNumOfLayersRemaining(numOfLayersRemaining);
    }

    @Override
    protected void start() {
        s.Mine();
    }

}
