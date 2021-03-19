package hu.holyoil.testframework;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.testcase.ITestcase;
import hu.holyoil.testcase.WaterEvaporatesTestcase;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class TestFramework {

    private static TestFramework testFramework;

    private List<ITestcase> testcases;

    public void AddTestcase(ITestcase testcase) {
        testcases.add(testcase);
    }

    // Loads all testcases
    // See: https://dzone.com/articles/get-all-classes-within-package
    public void AddTestcases() {

        // We get a classloader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {

            // We load the testcases
            Enumeration<URL> resources = classLoader.getResources("hu/holyoil/testcase");
            URL directoryURL = resources.nextElement();
            File directory = new File(directoryURL.getFile());

            // Just to be sure
            if (directory.exists()) {

                // We iterate through the files
                for (File file : directory.listFiles()) {

                    // It may be null
                    if (file == null) {
                        continue;
                    }

                    // If it is not the interface, we create an instance of it. The static initializator block should call
                    // the AddTestcase() of this class.
                    if (file.getName().endsWith(".class") && !file.getName().equalsIgnoreCase("ITestcase.class")) {

                        System.out.println("Found class: " + file.getName());
                        Class.forName("hu.holyoil.testcase." + file.getName().substring(0, file.getName().length() - 6));

                    }

                }

            }

        } catch (Exception exception) {

            // We should not reach this, but it is here nevertheless.
            exception.printStackTrace();

        }

    }

    public void RunTestcases() {
        // System.out.println("I have " + testcases.size() + " ITestcases");

        testcases.forEach(ITestcase::runTestcase);

    }

    public Asteroid GetAsteroid() {
        return new Asteroid();
    }

    public static TestFramework getInstance() {

        if (testFramework == null) {
            testFramework = new TestFramework();
        }

        return testFramework;

    }

    private TestFramework() {
        testcases = new ArrayList<>();
    }

}
