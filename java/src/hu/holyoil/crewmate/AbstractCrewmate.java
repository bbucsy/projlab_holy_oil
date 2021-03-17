package hu.holyoil.crewmate;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import jdk.jshell.spi.ExecutionControl;

public abstract class AbstractCrewmate {

    protected Asteroid onAsteroid;

    public void Move(INeighbour neighbour) {
        System.out.println("Moving to " + neighbour.toString());
    }

    public void SetOnAsteroid(Asteroid asteroid) {
        System.out.println("Setting my onAsteroid to " + asteroid.toString());
    }

    public void Drill()  {
        System.out.println("Drilling");
    }

    public abstract void Die();
    public abstract void ReactToAsteroidExplosion();

}
