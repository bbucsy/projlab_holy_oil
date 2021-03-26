package hu.holyoil.crewmate;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.skeleton.Logger;

/**
 * A robot és telepes közös őse, az űrhajó leszármazottja
 * tartalmaz absztrakt metódusokat,
 * nem lehet példányosítani
 */
public abstract class AbstractCrewmate extends AbstractSpaceship{
    /**
     * A Crewmate egy egységet fúr az aszteroida köpenyén
     * <p>meghívja az aszteroida ReactToDrill metódusát,
     *                      Az aszteroida lekezeli a kéregvastagság csökkenést</p>
     */
    public void Drill() {
        Logger.Log(this, "Drilling");
        onAsteroid.ReactToDrill();
        Logger.Return();
    }
}
