package hu.holyoil.skeleton;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * Tesztelést elősegítő keretrendszer, amely kezeli a tesztek indulását, betöltését, kérések kiszolgálását.
 */
public class TestFramework {

    /**
     * Singleton osztály, így saját magát statikusan példányosítja.
     */
    private static TestFramework testFramework;

    /**
     * Helyi szkenner tagváltozó, amely a bemenetet kezeli.
     */
    private Scanner scanner;

    /**
     * A keretrendszerbe betöltött tesztesetek listája.
     */
    private List<TestCase> testcases;

    /**
     * Tesztek hozzáadására szolgáló metódus
     * @param testcase A hozzáadni kívánt teszteset
     */
    public void AddTestcase(TestCase testcase) {
        testcases.add(testcase);
    }

    /**
     * Automatikusan a projektben írt teszteset osztályokból példányosító metódus, amely be is tölti azokat a keretrendszerbe.
     * Forrás: https://dzone.com/articles/get-all-classes-within-package
     */
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
                for (File file : Objects.requireNonNull(directory.listFiles())) {

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

    /**
     * A betöltött tesztesetek elindítását szolgáló metódus, amely kommunikál a felhasználóval.
     */
    public void RunTestcases() {

        scanner = new Scanner(System.in);

        while (true) {

            System.out.println("--------------------------------");
            System.out.println("Testcases:");

            for (int i = 0; i < testcases.size(); i++) {

                System.out.println(i + ".\t" + testcases.get(i).Name());

            }

            System.out.println("-1.\tExit Application");
            System.out.print("> Choose a testcase number to run: ");
            int numToRun = scanner.nextInt();

            if (numToRun == -1)
                return;

            while (numToRun < -1 || numToRun >= testcases.size()) {

                System.out.print("> Choose a valid number: ");
                numToRun = scanner.nextInt();

            }

            System.out.println("--------------------------------");
            testcases.get(numToRun).runTestcase();

            try {
                System.out.println("Press ENTER to choose another testcase...");
                //noinspection ResultOfMethodCallIgnored
                System.in.read();
            } catch (Exception ignore) {

            }

        }

    }

    /**
     * Bevett szokás, singleton osztály "példányosító" függvénye.
     * @return a statikusan példányosított keretrendszer objektum
     */
    public static TestFramework getInstance() {

        if (testFramework == null) {
            testFramework = new TestFramework();
        }

        return testFramework;

    }

    /**
     * Kívülről nem elérhető konstruktor, a singleton tulajdonság védelmére.
     */
    private TestFramework() {
        testcases = new ArrayList<>();
    }

}
