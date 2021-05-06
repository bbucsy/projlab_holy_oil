package hu.holyoil.controller;

import java.io.PrintStream;
import java.util.HashMap;


/**
 * A skeleton program input/output szükségleteit kielégítő statikus osztály.
 * Nyilvántartja a tesztesetben résztvevő részleges model objektumait, és neveit.
 * Kezeli a függvényhívások szép megjelenítéséhez szükséges indentálást.
 */
public final class Logger {

    /**
     * Privát konstruktor, hogy ne lehessen példányosítani az osztályt.
     */
    private Logger(){}

    /**
     * A tesztesetben szereplő objektumok neveit tárolja.
     */
    private static HashMap<Object,String> objectNames = new HashMap<>();

    /**
     * Erre a printstreamre ír a logger
     * */
    private static PrintStream printStream = System.out;

    /**
     * Felülírja a printstreamet
     * */
    public static void SetPrintStream(PrintStream ps) { printStream = ps; }

    /**
     * Eltárolja egy objektumról, hogy a milyen névvel jelenjen meg a teszteset outputjában.
     * Egy objektumnak csak egy neve lehet.
     * @param o Az objektum aminek a nevét tároljuk
     * @param name Az objektum neve
     */
    public static void RegisterObject(Object o, String name){
        objectNames.put(o,name);
    }

    /**
     * Kiír egy sort, a  megfelelő formázással, indentálással és a hívó nevének megjelenítésével.
     * Ha a Logger osztály nincs engedélyezve a függvény hatása nem, érvényesül.
     * @param caller Az az objektum, ami a kiírt sor elején kell szerepeljen.
     * @param msg A hívó neve után kiírandó szöveg kiírandó szöveg.
     */
    public static void Log(Object caller, String msg){
        if(!objectNames.containsKey(caller)){
            return;
        }

        printStream.print("> " + objectNames.get(caller) + ": " + msg + "\n");
    }

}
