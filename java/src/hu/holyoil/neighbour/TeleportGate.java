package hu.holyoil.neighbour;

import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.storage.PlayerStorage;

/**
 * Teleportereket leíró osztály.
 * Implementálja az INegihbour interfacet.
 */
public class TeleportGate implements INeighbour {
    /**
     * Paraméter nélküli konstruktor.
     * A létrejövő teleporternek nincs párja, nincs benne semmilyen tárolóban, és nincs rajta egy aszteroidán sem.
     */
    public TeleportGate() {
        pair = null;
        homeAsteroid = null;
        homeStorage = null;
    }

    /**
     * A teleporter párja. két irányú kapcsolat.
     */
    private TeleportGate pair;
    /**
     * Az aszteroida amin a teleporter tartózkodik.
     */
    private Asteroid homeAsteroid;

    /**
     * Visszaadja a teleporter párját.
     * @return a teleporter párja
     */
    public TeleportGate GetPair() {
        return pair;
    }

    /**
     * Visszaadja az aszteroidát amin a teleporter található.
     * @return a teleporter homeAsteroid tagváltozója
     */
    public Asteroid GetHomeAsteroid() {
        return homeAsteroid;
    }

    /**
     * Beállítja a teleportert aszteroidáját.
     * @param homeAsteroid Az aszteroida amihez a teleporter mostantól tartozni fog.
     */
    public void SetHomeAsteroid(Asteroid homeAsteroid) {
        this.homeAsteroid = homeAsteroid;
    }

    /**
     * Visszaadja a tárolót ami tartalmazza.
     * @return a játékos tárolója ahol tárolva van
     */
    public PlayerStorage GetHomeStorage() {
        return homeStorage;
    }

    /**
     * Beállítja az őt tartalmazó tárolót.
     * @param homeStorage egy játékos tárolója aki létrehozta
     */
    public void SetHomeStorage(PlayerStorage homeStorage) {
        this.homeStorage = homeStorage;
    }

    /**
     * A játékos tárolója ahol tartózkodik, amíg a játékos le nem teszi egy aszteroidára.
     * Ha le lett rakva egy aszteroidára a homeStorage értéke null.
     */
    private PlayerStorage homeStorage;

    /**
     * A rajta keresztül való mozgást kezeli.
     * <p>
     *     Ha egy crewmate át akar rajta lépni ellenőrzi, hogy a párja le van-e rakva. Ha nem, nem történik semmi.
     *     Ha igen, meghívja a párja aszteroidájának mozgásra való reakció metódusát, és a crewmate átlép arra az aszteroidára.
     * </p>
     * @param from az az aszteroida amin a Crewmate eredetileg tartózkodik, ebben az esetben a teleport saját aszteroidája
     * @param abstractCrewmate a mozgást elvégezni készülő Crewmate
     */
    @Override
    public void ReactToMove(Asteroid from, AbstractCrewmate abstractCrewmate) {

        Logger.Log(this, "Reacting to move from " + Logger.GetName(from) + " by " + Logger.GetName(abstractCrewmate));
        if (pair.GetHomeAsteroid() != null) {
            pair.GetHomeAsteroid().ReactToMove(from, abstractCrewmate);
        }
        Logger.Return();

    }

    /**
     * Beállítja a teleporter párját.
     * @param newPair a teleporter párja
     */
    public void SetPair(TeleportGate newPair) {

        Logger.Log(this, "Setting my pair to " + Logger.GetName(newPair));
        pair = newPair;
        Logger.Return();

    }

    /**
     * Felrobban.
     * <p>
     *     Felrobbantja a párját. Ez egy külön metódus, hogy ne legyen rekurzió.
     *     A velódi törléseket az ActuallyExplode() metódus intézi. Ez a metódus azt hívja.
     * </p>
     */
    @Override
    public void Explode() {

        Logger.Log(this, "Exploding");
        pair.ExplodePair();
        ActuallyExplode();
        Logger.Return();

    }

    /**
     * A teleporter párja tudja meghívni, ha őt felrobbantja valami. Nem hívja vissza az ő párjának robbantását.
     */
    private void ExplodePair() {

        Logger.Log(this, "Being exploded by pair");
        ActuallyExplode();
        Logger.Return();

    }

    /**
     * Törli magát vagy az aszteroidáról amihez tartozik, vagy a tárolóból amiben van. Egy teleporter nem lehete egyszerre aszteroidán és tárolóban.
     */
    private void ActuallyExplode() {
        if ((homeAsteroid == null && homeStorage == null) ||(homeAsteroid != null && homeStorage != null)) {
            // Error
            Logger.Log(this, "An error occured");
        }
        if ((homeAsteroid == null && homeStorage != null)) {
            // in storage
            homeStorage.RemoveTeleportGate(this);
        } else {
            // this line is needed for idea to stfu
            if (homeAsteroid != null) {
                homeAsteroid.RemoveTeleporter();
            }
        }
    }

}
