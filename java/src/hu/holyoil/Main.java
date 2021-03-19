package hu.holyoil;

import hu.holyoil.skeleton.TestFramework;

public class Main {

    public static final Boolean isTestMode = true;

    public static void main(String[] args) {
        // System.out.println("Hello world");

        //TestCase t = new Example();
        //t.PlayScenario();


        if (isTestMode) {
            TestFramework.getInstance().AddTestcases();
            TestFramework.getInstance().RunTestcases();
        }

    }

}
