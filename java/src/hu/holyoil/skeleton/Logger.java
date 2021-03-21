package hu.holyoil.skeleton;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class Logger {

    private Logger(){}

    private static HashMap<Object,String> objectNames = new HashMap<>();
    private static int indentation = 0;
    private static Scanner in = new Scanner(System.in);
    private static boolean enabled = true;

    private static void print(String msg, int indentation) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < indentation; ++i) {
            spaces.append("\t");
        }
        System.out.print(spaces.toString() + msg);
    }

    public static void SetEnabled(boolean e){
        enabled = e;
    }

    public static void RegisterObject(Object o, String name){
        objectNames.put(o,name);
    }

    public static String GetName(Object object){
        return objectNames.get(object);
    }

    public static void ClearObjects(){
        objectNames.clear();
        indentation = 0;
    }

    public static void Log(Object caller, String msg){
        if (!enabled) return;
        if(!objectNames.containsKey(caller)){
            System.out.println("Unregistered object calling");
            return;
        }
        print(objectNames.get(caller)+": -> " + msg + "\n", indentation++);
    }

    public static void Return(){
        if (!enabled) return;
        if (indentation > 0)
            --indentation;
    }

    public static int GetInteger(Object caller, String msg){
        if(!objectNames.containsKey(caller)){
            System.out.println("Unregistered object calling");
            return 0;
        }

        print(("> " + objectNames.get(caller)+": " + msg).trim() + " ", indentation);
        int result = 0;
        try {
            result = in.nextInt();
        }
        catch (InputMismatchException e) {
            print("[InputMismatchException] Input interpreted as 0...\n", indentation);
            in.next();
        }
        return result;
    }

    public static Boolean GetBoolean(Object caller, String msg){
        if(!objectNames.containsKey(caller)){
            System.out.println("Unregistered object calling");
            return Boolean.FALSE;
        }

        print(("> " + objectNames.get(caller)+": " + msg).trim() + " [true/false] ", indentation);
        boolean result = false;
        try {
            result = in.nextBoolean();
        }
        catch (InputMismatchException e) {
            print("[InputMismatchException] Input interpreted as false...\n", indentation);
            in.next();
        }
        return result;
    }

}
