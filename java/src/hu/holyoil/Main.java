package hu.holyoil;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.Coal;

public class Main {

    public static final Boolean isTestMode = true;

    public static void main(String[] args) {

        System.out.println("Hello world");
        Coal coal = new Coal();
        Asteroid asteroid = new Asteroid();
        Settler settler = new Settler(asteroid);
        asteroid.AddCrewmate(settler);
        settler.Move(asteroid);
        coal.ReactToMine(asteroid, settler);

    }

}
