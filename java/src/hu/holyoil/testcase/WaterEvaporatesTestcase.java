package hu.holyoil.testcase;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Water;
import hu.holyoil.testframework.TestFramework;

public class WaterEvaporatesTestcase implements ITestcase {

    static {
        System.out.println("I am being initialized for real");
        TestFramework.getInstance().AddTestcase(new WaterEvaporatesTestcase());
    }

    @Override
    public void runTestcase() {

        Asteroid asteroid = new Asteroid();
        Settler settler = new Settler(asteroid);
        asteroid.SetResource(
                new Water()
        );
        asteroid.SetIsNearbySun(
                Boolean.TRUE
        );
        asteroid.ReactToSunNearby();

    }

}
