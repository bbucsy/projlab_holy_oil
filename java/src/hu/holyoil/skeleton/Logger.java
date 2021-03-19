package hu.holyoil.skeleton;

import java.util.HashMap;
import java.util.Scanner;

public final class Logger {

    private Logger(){}

    private static HashMap<Object,String> objectNames = new HashMap<>();
    private static int indentation = 0;
    private static Scanner in = new Scanner(System.in);
    private static boolean enabled = true;


    public static void SetEnabled(boolean e){
        enabled = e;
    }

    public static void RegisterObject(Object o, String name){
        objectNames.put(o,name);
    }

    public static String GetName(Object object){ return objectNames.get(object); }

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
        System.out.print((objectNames.get(caller)+": -> " + msg).indent(indentation++));
    }

    public static void Return(){
        indentation--;
    }

    public static int GetInteger(Object caller, String msg){
        if(!objectNames.containsKey(caller)){
            System.out.println("Unregistered object calling");
            return 0;
        }

        System.out.print((objectNames.get(caller)+": " + msg).indent(indentation-1).stripTrailing() + " ");
        int res = in.nextInt();
        return res;
    }

}
