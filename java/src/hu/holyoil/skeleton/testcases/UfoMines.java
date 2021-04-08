package hu.holyoil.skeleton.testcases;

import hu.holyoil.crewmate.Ufo;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Uranium;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestCase;

/**
 * Az UFO kibányássza egy aszteroida nyersanyagát, üresen hagyva azt.
 * Ebben a tesztesetben példaként uránt használunk nyersanyagként. Ez akármilyen nyersanyag lehet.
 */
public class UfoMines extends TestCase {
    Ufo ufo;
    @Override
    public String Name() {
        return "UFO Mines";
    }

    @Override
    protected void load() {
        Asteroid a= new Asteroid("onAsteroid");
        ufo= new Ufo(a, "ufo");
        Uranium uran= new Uranium("uranium");

        a.SetResource(uran);
    }

    @Override
    protected void start() {
        ufo.Mine();
    }
}
