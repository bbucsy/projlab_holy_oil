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
    /**
     * Beállítja az aszteroidát amin éppen van az űrhajó, és felfedezi azt.
     * @param asteroid A beállítandó aszteroida
     */
    @Override
    public void SetOnAsteroid(Asteroid asteroid) {
        Logger.Log(this, "Setting onAsteroid to " + Logger.GetName(asteroid));
        onAsteroid = asteroid;
        onAsteroid.Discover();
        Logger.Return();
    }
    
}
