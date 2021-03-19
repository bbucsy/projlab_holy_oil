package hu.holyoil.crewmate;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import jdk.jshell.spi.ExecutionControl;

public abstract class AbstractCrewmate {

    protected Asteroid onAsteroid;

    public void Move(INeighbour neighbour) {
        System.out.println("I am crewmate " + this.toString() + " Moving to " + neighbour.toString());
        neighbour.ReactToMove(onAsteroid, this);
    }

    public void SetOnAsteroid(Asteroid asteroid) {
        System.out.println("I am crewmate " + this.toString() + " Setting my onAsteroid to " + asteroid.toString());
    }

    public void Drill()  {
        System.out.println("I am crewmate " + this.toString() + " and I am Drilling");
        onAsteroid.ReactToDrill();
    }

    public abstract void Die();
    public abstract void ReactToAsteroidExplosion();

}
