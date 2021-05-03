package hu.holyoil.crewmate;

import hu.holyoil.IIdentifiable;
import hu.holyoil.commandhandler.Logger;
import hu.holyoil.controller.TurnController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;

import java.awt.Image;

/**
 * A telepes, robot és UFO közös őse
 * Nem lehet példányosítani, nem tud fúrni
 */
public abstract class AbstractSpaceship implements IStepping, IIdentifiable{

    /**
     * Azon aszteroida amin a Crewmate jelenleg tartózkodik
     */
    protected Asteroid onAsteroid;

    /**
     * Spaceship egyedi azonosítója
     * */
    protected String id;

    /**
     * Visszaadja a hajó egyedi azonosítóját
     * */
    public String GetId() {
        return id;
    }

    /**
     * Az űrhajó átmegy a jelen aszteroida egy szomszédjára
     * <p>meghívja a szomszéd ReactToMove metódusát. Az aszteroida váltást a szomszéd aszteroida kezeli</p>
     * @param neighbour az aszteroida egy elérhető szomszédja
     */
    public void Move(INeighbour neighbour) {

        if (TurnController.GetInstance().HasNoActionsLeft(this)) {
            Logger.Log(this, "Cannot move, no more moves left this turn");
            Logger.Return();
            return;
        }

        Logger.Log(this, "Moving to " + Logger.GetName(neighbour));

        //noinspection SuspiciousMethodCalls
        if (onAsteroid.GetNeighbours().contains(neighbour) || onAsteroid.GetTeleporter() == neighbour) {
            neighbour.ReactToMove(onAsteroid, this);
        } else {
            Logger.Log(this, "Cannot move to " + Logger.GetName(neighbour) + ", it is not a neighbour of my asteroid");
            Logger.Return();
        }

        Logger.Return();
    }

    /**
     * Visszaadja a jelenleg elfoglalt aszteroidáját
     * @return aszteroidája
     */
    public Asteroid GetOnAsteroid() {
        return onAsteroid;
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
     * Végrehajtott egy műveletet.
     * */
    public void ReactToMoveMade() {
        TurnController.GetInstance().ReactToActionMade(this);
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

    /**
     * Visszaadja az ikonját
     */
    public abstract Image GetImage();
}
