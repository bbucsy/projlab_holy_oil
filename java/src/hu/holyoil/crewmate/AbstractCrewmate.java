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
        onAsteroid = asteroid;
    }

    public void Drill()  {
        Logger.Log(this,"Drilling");

        onAsteroid.ReactToDrill();

        Logger.Return();
    }

    public abstract void Die();
    public abstract void ReactToAsteroidExplosion();

}
