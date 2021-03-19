package hu.holyoil;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Coal;
import hu.holyoil.testframework.TestFramework;

public class Main {

    public static final Boolean isTestMode = true;

    public static void main(String[] args) {

        System.out.println("Hello world");

        if (isTestMode) {
            TestFramework.getInstance().AddTestcases();
            TestFramework.getInstance().RunTestcases();
        }

    }

}
