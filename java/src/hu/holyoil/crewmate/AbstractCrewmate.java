package hu.holyoil.crewmate;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.skeleton.Logger;

/**
 * A robot és telepes közös őse
 * tartalmaz absztrakt metódusokat
 * nem lehet példányosítani
 */
public abstract class AbstractCrewmate {
    /**
     * azon aszteroida amin a Crewmate jelenleg tartózkodik
     */
    protected Asteroid onAsteroid;

    /**
     * A Crewmate átmegy a jelen aszteroida egy szomszédjára
     * @param neighbour az aszteroida egy elérhető szomszédja
     * meghívja a szomszéd ReactToMove metódusát
     *                  Az aszteroida váltást a szomszéd aszteroida kezeli
     */
    public void Move(INeighbour neighbour) {
        Logger.Log(this, "Moving to " + Logger.GetName(neighbour));
        neighbour.ReactToMove(onAsteroid, this);
        Logger.Return();
    }

    /**
     * Beállítja az aszteroidát amin éppen van a Crewmate
     * @param asteroid A beállítandó aszteroida
     */
    public void SetOnAsteroid(Asteroid asteroid) {
        Logger.Log(this, "Setting onAsteroid to " + Logger.GetName(asteroid));
        onAsteroid = asteroid;
        Logger.Return();
    }

    /**
     * A Crewmate egy egységet fúr az aszteroida köpenyén
     * meghívja az aszteroida ReactToDrill metódusát
     *                      Az aszteroida lekezeli a kéregvastagság csökkenést
     */
    public void Drill() {
        Logger.Log(this, "Drilling");
        onAsteroid.ReactToDrill();
        Logger.Return();
    }

    /**
     * Máshogy történik a leszármazottak halála
     *          leszármazottak maguk realizálják
     */
    public abstract void Die();

    /**
     * Máshogy reagálnak a leszármazottak a robbanásra
     *          leszármazottak maguk realizálják
     */
    public abstract void ReactToAsteroidExplosion();

}
