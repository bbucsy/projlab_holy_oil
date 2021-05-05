package hu.holyoil.crewmate;

import hu.holyoil.controller.TurnController;
import hu.holyoil.neighbour.Asteroid;

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

            return;

        }

        onAsteroid.ReactToDrill(this);
    }
    /**
     * Beállítja az aszteroidát amin éppen van az űrhajó, és felfedezi azt.
     * @param asteroid A beállítandó aszteroida
     */
    @Override
    public void SetOnAsteroid(Asteroid asteroid) {
        onAsteroid = asteroid;
        onAsteroid.Discover();
    }
}
