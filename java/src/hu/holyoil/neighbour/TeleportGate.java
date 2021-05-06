package hu.holyoil.neighbour;

import hu.holyoil.controller.AIController;
import hu.holyoil.controller.Logger;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.repository.NeighbourBaseRepository;
import hu.holyoil.storage.PlayerStorage;

/**
 * Teleportereket leíró osztály.
 * Implementálja az INegihbour interfacet.
 */
public class TeleportGate implements INeighbour {

    /**
     * Meghívja a konstruktort egy generált id-vel, amennyiben az nincs előre meghatározva.
     */
    public TeleportGate() {
        this(NeighbourBaseRepository.GetIdWithPrefix("TeleportGate"));
    }

    /**
     * Paraméter nélküli konstruktor.
     * A létrejövő teleporternek nincs párja, nincs benne semmilyen tárolóban, és nincs rajta egy aszteroidán sem.
     */
    public TeleportGate(String name) {

        pair = null;
        homeAsteroid = null;
        homeStorage = null;
        isCrazy = false;
        id = name;
        NeighbourBaseRepository.GetInstance().Add(name, this);

    }

    /**
     * A teleportkaput azonosító egyedi azonosító.
     * */
    private String id;

    /**
     * Visszaadja a teleportkapu egyedi azonosítóját.
     * */
    public String GetId() {
        return id;
    }

    /**
     * Kiírja a teleportkaput emberileg olvasható módon. Az asszociációk helyén id-k szerepelnek.
     * */
    @Override
    public String toString() {

        return "TELEPORTGATE (name:) " + id
                + "\n\t(is crazy:) " + isCrazy
                + "\n\t(pair name:) " + pair.GetId()
                + "\n\t(asteroid name:) " + (homeAsteroid == null ? "null" : homeAsteroid.GetId())
                + "\n\t(storage name:) " + (homeStorage == null ? "null" : homeStorage.GetId());

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
     * Mutatja érte-e már napvihar a teleportert.
     */
    private boolean isCrazy;
    /**
     * Visszaadja a teleporter párját.
     * @return a teleporter párja
     */
    public TeleportGate GetPair() {
        return pair;
    }

    /**
     * Beállítja, hogy meg van-e kergülve a teleportkapu. Igaz esetben be is kerül az AI kontrollerbe, hogy az
     * gondoskodjon a mozgásáról.
     * @param newIsCrazy új érték
     */
    public void SetIsCrazy(boolean newIsCrazy) {

        isCrazy = newIsCrazy;
        if (isCrazy) AIController.GetInstance().AddTeleportGate(this);

    }

    /**
     * Visszaadja a teleporter kerge állapotát.
     * @return true: már érte napvihar, false: még nem érte napvihar
     */
    public boolean GetIsCrazy(){
        return isCrazy;
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
     *     Ha egy Spaceship át akar rajta lépni ellenőrzi, hogy a párja le van-e rakva. Ha nem, nem történik semmi.
     *     Ha igen, meghívja a párja aszteroidájának mozgásra való reakció metódusát, és a Spaceship átlép arra az aszteroidára.
     * </p>
     * @param from az az aszteroida amin a Spaceship eredetileg tartózkodik, ebben az esetben a teleport saját aszteroidája
     * @param abstractSpaceship a mozgást elvégezni készülő Spaceship
     */
    @Override
    public void ReactToMove(Asteroid from, AbstractSpaceship abstractSpaceship) {
        if (pair.GetHomeAsteroid() != null) {
            pair.GetHomeAsteroid().ReactToMove(from, abstractSpaceship);
        }
    }

    /**
     * Beállítja a teleporter párját.
     * @param newPair a teleporter párja
     */
    public void SetPair(TeleportGate newPair) {
        pair = newPair;
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
        pair.ExplodePair();
        ActuallyExplode();
        NeighbourBaseRepository.GetInstance().Remove(this.id);
    }

    /**
     * A teleporter párja tudja meghívni, ha őt felrobbantja valami. Nem hívja vissza az ő párjának robbantását.
     */
    private void ExplodePair() {
        ActuallyExplode();
        NeighbourBaseRepository.GetInstance().Remove(this.id);
    }

    /**
     * Törli magát vagy az aszteroidáról amihez tartozik, vagy a tárolóból amiben van. Egy teleporter nem lehete egyszerre aszteroidán és tárolóban.
     */
    private void ActuallyExplode() {

        if ((homeAsteroid == null && homeStorage != null)) {
            // in storage
            homeStorage.RemoveTeleportGate(this);
        } else {
            // this line is needed for idea to stfu
            if (homeAsteroid != null) {
                homeAsteroid.RemoveTeleporter();
                AIController.GetInstance().RemoveTeleportGate(this);
            }
        }
        NeighbourBaseRepository.GetInstance().Remove(id);
        Logger.Log(this, "Exploded");

    }

    /**
     * Napvihar hatására a teleporter megkergül, és innentől a játék végéig kerge marad.
     */
    @Override
    public void ReactToSunstorm() {
        SetIsCrazy(true);
    }
    /**
     * Teleport kapu mozog egy olyan szomszédos aszteroidára, aminek nincs teleportere, ha meg van kergülve.
     */
    public void Move(Asteroid asteroid){
        if (isCrazy && homeAsteroid.GetNeighbours().contains(asteroid)) {
            asteroid.ReactToMove(this);
        }
    }

}
