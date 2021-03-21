package hu.holyoil.crewmate;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.skeleton.Logger;

public abstract class AbstractCrewmate {

    protected Asteroid onAsteroid;

    public void Move(INeighbour neighbour) {
        Logger.Log(this, "Moving to " + Logger.GetName(neighbour));

        if (onAsteroid.GetNeighbours().contains(neighbour)) {
            neighbour.ReactToMove(onAsteroid, this);
        } else {
            Logger.Log(this, "Cannot move to " + Logger.GetName(neighbour) + ", it is not a neighbour of my asteroid");
            Logger.Return();
        }

        Logger.Return();
    }

    public void SetOnAsteroid(Asteroid asteroid) {
        Logger.Log(this, "Setting onAsteroid to " + Logger.GetName(asteroid));
        onAsteroid = asteroid;
        Logger.Return();
    }

    public void Drill() {
        Logger.Log(this, "Drilling");
        onAsteroid.ReactToDrill();
        Logger.Return();
    }

    public abstract void Die();

    public abstract void ReactToAsteroidExplosion();

}
