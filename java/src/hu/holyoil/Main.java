package hu.holyoil;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Coal;
import hu.holyoil.testframework.TestFramework;

import hu.holyoil.skeleton.TestCase;
import hu.holyoil.skeleton.testcases.Example;

public class Main {

    public static final Boolean isTestMode = true;

    public static void main(String[] args) {

        TestCase t = new Example();
        t.PlayScenario();


        if (isTestMode) {
            TestFramework.getInstance().AddTestcases();
            TestFramework.getInstance().RunTestcases();
        }

    }

}
