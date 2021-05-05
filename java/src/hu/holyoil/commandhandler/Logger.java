package hu.holyoil.commandhandler;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;


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
     * A következő logolásnál szükséges indentáció mértékét tartja számon.
     */
    private static int indentation = 0;

    /**
     * Használjon-e indentálást logoláskor.
     * */
    private static final boolean indentationEnabled = false;

    /**
     * Adatok beolvasásához szükésges objektum.
     */
    private static Scanner in = new Scanner(System.in);

    /**
     * Ha az értéke hamis, a `Log()` hívások nem csinálnak semmit.
     */
    private static boolean enabled = true;

    /**
     * Erre a printstreamre ír a logger
     * */
    private static PrintStream printStream = System.out;

    /**
     * Felülírja a printstreamet
     * */
    public static void SetPrintStream(PrintStream ps) { printStream = ps; }

    /**
     * Privát függvény a szöveg megfelelő indentálásához
     * @param msg Indentálandó szöveg
     * @param indentation Indentálás mértéke
     */
    private static void print(String msg, int indentation) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < indentation; ++i) {
            spaces.append("\t");
        }
        printStream.print(spaces.toString() + msg);
    }

    /**
     * Be és kikapcsolja, hogy a `Log()` meghívása kiír -e szöveget
     * @param e Ha false, a Logger nem ír ki szöveget
     */
    public static void SetEnabled(boolean e){
        enabled = e;
    }

    /**
     * Visszaadja, be van-e kapcsolva a `Log()`-ok meghívásakor való kiírás
     * @return a Logger ír-e ki szöveget
     */
    public static boolean IsEnabled(){
        return enabled;
    }

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
     * Visszaadja az objektum nevét.
     * Ha az objektum nincs eltárolva a visszatérési érték `null`.
     * @param object Az objektum aminek a nevére kíváncsiak vagyunk.
     * @return Az objektum neve.
     */
    public static String GetName(Object object){
        return objectNames.get(object);
    }

    /**
     * Kitörli az összes eltárolt objektum nevét,
     * és visszaállítja az indentálást az alapértelmezett értékre
     */
    public static void ClearObjects(){
        objectNames.clear();
        indentation = 0;
    }

    /**
     * Kiír egy sort, a  megfelelő formázással, indentálással és a hívó nevének megjelenítésével.
     * Ha a Logger osztály nincs engedélyezve a függvény hatása nem, érvényesül.
     * @param caller Az az objektum, ami a kiírt sor elején kell szerepeljen.
     * @param msg A hívó neve után kiírandó szöveg kiírandó szöveg.
     */
    public static void Log(Object caller, String msg){
        if (!enabled) return;
        if(!objectNames.containsKey(caller)){
            return;
        }

        if (indentationEnabled) {
            print(objectNames.get(caller) + ": -> " + msg + "\n", indentation++);
        } else {
            print(objectNames.get(caller) + ": -> " + msg + "\n", 0);
        }
    }

    /**
     * Eggyel csökkenti az indentációt.
     * Ha a Logger osztály nincs engedélyezve a függvény hatása nem, érvényesül.
     */
    public static void Return(){
        if (!enabled) return;
        if (indentation > 0)
            --indentation;
    }

}
