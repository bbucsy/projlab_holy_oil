package hu.holyoil.crewmate;

import hu.holyoil.IIdentifiable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.skeleton.Logger;

/**
 * A telepes, robot és UFO közös őse
 * Nem lehet példányosítani, nem tud fúrni
 */
public abstract class AbstractSpaceship implements IIdentifiable {
    /**
     * Azon aszteroida amin a Crewmate jelenleg tartózkodik
     */
    protected Asteroid onAsteroid;

    /**
     * Spaceship egyedi azonosítója
     * */
    protected int id;

    /**
     * Visszaadja a hajó egyedi azonosítóját
     * */
    public int GetId() {
        return id;
    }

    /**
     * Az űrhajó átmegy a jelen aszteroida egy szomszédjára
     * <p>meghívja a szomszéd ReactToMove metódusát. Az aszteroida váltást a szomszéd aszteroida kezeli</p>
     * @param neighbour az aszteroida egy elérhető szomszédja
     */
    public void Move(INeighbour neighbour) {
        Logger.Log(this, "Moving to " + Logger.GetName(neighbour));

        if (onAsteroid.GetNeighbours().contains(neighbour) || onAsteroid.GetTeleporter() == neighbour) {
            neighbour.ReactToMove(onAsteroid, this);
        } else {
            Logger.Log(this, "Cannot move to " + Logger.GetName(neighbour) + ", it is not a neighbour of my asteroid");
            Logger.Return();
        }

        Logger.Return();
    }

    /**
     * Beállítja az aszteroidát amin éppen van az űrhajó
     * @param asteroid A beállítandó aszteroida
     */
    public void SetOnAsteroid(Asteroid asteroid) {
        Logger.Log(this, "Setting onAsteroid to " + Logger.GetName(asteroid));
        onAsteroid = asteroid;
        Logger.Return();
    }
    /**
     * Máshogy történik a leszármazottak halála
     *          (leszármazottak maguknak realizálják)
     */
    public abstract void Die();

    /**
     * Máshogy reagálnak a leszármazottak a robbanásra
     *          (leszármazottak maguk realizálják)
     */
    public abstract void ReactToAsteroidExplosion();

}
