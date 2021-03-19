package hu.holyoil.skeleton;

import hu.holyoil.neighbour.Asteroid;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class TestFramework {

    private static TestFramework testFramework;

    private List<TestCase> testcases;

    public void AddTestcase(TestCase testcase) {
        testcases.add(testcase);
    }

    // Loads all testcases
    // See: https://dzone.com/articles/get-all-classes-within-package
    public void AddTestcases() {

        // We get a classloader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {

            // We load the testcases
            Enumeration<URL> resources = classLoader.getResources("hu/holyoil/skeleton/testcases");
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
                    if (file.getName().endsWith(".class")) {

                        // System.out.println("Found class: " + file.getName());
                        // **magic**
                        Class.forName("hu.holyoil.skeleton.testcases." + file.getName().substring(0, file.getName().length() - 6)).getDeclaredConstructor().newInstance();

                    }

                }

            }

        } catch (Exception exception) {

            // We should not reach this, but it is here nevertheless.
            exception.printStackTrace();

        }

    }

    public void RunTestcases() {

        testcases.forEach(TestCase::runTestcase);

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
