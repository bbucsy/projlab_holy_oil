package hu.holyoil.testframework;

import hu.holyoil.neighbour.Asteroid;

public class TestFramework {

    private static TestFramework testFramework;

    public Asteroid GetAsteroid() {
        return new Asteroid();
    }

    public static TestFramework getInstance() {

        if (testFramework == null) {
            testFramework = new TestFramework();
        }

        return testFramework;

    }

}
