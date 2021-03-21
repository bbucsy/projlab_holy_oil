package hu.holyoil;

import hu.holyoil.skeleton.TestFramework;

/**
 * A program futására írt osztály, amelyben a main metódus lakik.
 */
public class Main {

    /**
     * Segéd-tagváltozó, amelynek segítségével be tudjuk állítani, tesztelő üzemmódban induljon a program.
     */
    public static final Boolean isTestMode = true;

    /**
     * A program központi indító függvénye.
     * @param args program indításakor megadott argumentumok
     */
    public static void main(String[] args) {

        if (isTestMode) {
            TestFramework.getInstance().AddTestcases();
            TestFramework.getInstance().RunTestcases();
        }

    }

}
