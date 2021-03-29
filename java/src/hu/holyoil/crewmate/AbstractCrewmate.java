package hu.holyoil.crewmate;

import hu.holyoil.controller.TurnController;
import hu.holyoil.neighbour.Asteroid;
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

        if (TurnController.GetInstance().HasNoActionsLeft(this)) {

            Logger.Log(this, "Cannot drill, no more moves left");
            Logger.Return();
            return;

        }

        Logger.Log(this, "Drilling");
        onAsteroid.ReactToDrill(this);
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
