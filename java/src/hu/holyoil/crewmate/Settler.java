package hu.holyoil.crewmate;

import hu.holyoil.controller.GameController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.recipe.RobotRecipe;
import hu.holyoil.recipe.TeleporterRecipe;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.storage.PlayerStorage;

/**
 * A Telepeseket leíró osztály.
 * Leszármazottja az AbstractCrewmate-nek (robottal közös tulajdonságai miatt).
 * Implementálja az IStorageCapable-t mert képes tárolásra és gyártásra.
 */
public class Settler extends AbstractCrewmate implements IStorageCapable {
    /**
     * Létrehoz egy telepest
     * <p>Kívülről nem elérhető: nem lehet kezdő Aszteroida nélkül példányosítani.
     * Nem használjuk jelenleg sehol: biztonságból van: ne lehessen storage nélkül létrehozni settlert sehol</p>
     */
    private Settler() {
        storage = new PlayerStorage();
    }

    /**
     * Settler konstruktora
     * <p>inicializálja a storage-ot,
     * hozzáadja az aszteroidához a telepest</p>
     * @param startingAsteroid Aszteroida amelyen létrehozza a settlert
     *
     */
    public Settler(Asteroid startingAsteroid) {
        storage = new PlayerStorage();
        onAsteroid = startingAsteroid;
        onAsteroid.AddCrewmate(this);
    }


    /**
     * A telepes saját inventoryja
     */
    private PlayerStorage storage;

    /**
     * A telepes meghal
     * <p>felülírja az AbstractCrewmate Die() metódusát.
     * Eltávolítja a telepest a GameControllerből.
     * Ha a telepesnél van legalább egy teleporter azt felrobbantja (ami felrobbantja a párját).
     * Az aszteroidáról is kitörli a telepest</p>
     */
    @Override
    public void Die() {
        Logger.Log(this, "Died");

        GameController.GetInstance().RemoveSettler(this);

        if (storage.GetOneTeleporter() != null) {
            storage.GetOneTeleporter().Explode();
        }

        onAsteroid.RemoveCrewmate(this);
        Logger.Return();
    }

    /**
     * Az aszteroida által meghívandó függvény, ha az aszteroida felrobban.
     * <p>Meghívja a Die() függvényt.</p>
     */
    @Override
    public void ReactToAsteroidExplosion() {
        Logger.Log(this, "Reacting to asteroid explosion");
        Die();
        Logger.Return();
    }

    /**
     * Robot készítése
     * <p>a tényleges robot gyártás és feltétel ellenőrzést a RobotRecipe végzi.
     * Meghívja a RobotRecipe() singleton osztály Craft() metódusát</p>
     */
    @Override
    public void CraftRobot() {
        Logger.Log(this, "Crafting robot");
        RobotRecipe.GetInstance().Craft(this, onAsteroid);
        Logger.Return();
    }

    /**
     *Teleportkapu pár gyártása (mindig egyszerre kettőt)
     * <p>A tényleges teleport gyártás és feltétel ellenőrzést a TeleporterRecipe végzi.
     * Meghívja a TeleporterRecipe() singleton osztály Craft() metódusát</p>
     */
    @Override
    public void CraftTeleportGate() {
        Logger.Log(this, "Crafting teleport gate pair.");
        TeleporterRecipe.GetInstance().Craft(this, onAsteroid);
        Logger.Return();
    }

    /**
     * A telepes megpróbálja kibányászni a jelen aszteroida nyersanyagát
     * <p>Az aszteroida ReactToMineBy metódusa meghívja az AbstractBaseResource ReactToMine metódusát.
     * Az AbstractBaseResource kezeli le az aszteroida ürítését és az inventory feltöltését</p>
     */
    @Override
    public void Mine() {
        Logger.Log(this, "Mining");
        onAsteroid.ReactToMineBy(this);
        Logger.Return();
    }

    /**
     * @return visszatér a játékos inventoryjával a jelen állapotában
     */
    @Override
    public PlayerStorage GetStorage() {
        Logger.Log(this,"Returning " + Logger.GetName(storage));
        Logger.Return();
        return storage;
    }

    /**
     * A telepes megpróbál lerakni egy teleportert
     * <p>Ellenőrzi van-e nála legalább egy teleporter: ha nincs a storage.GetOneTeleporter null-al tér vissza. </p>
     * <p>Ellenőrzi az aszteroidán van e már teleporter: ha nincs, az onAsteroid.GetTeleporter null-al tér vissza.</p>
     * <p>csak akkor tehet le teleportert ha nála van legalább egy, és az aszteroidán még egy sincs</p>
     * <p>ha ez teljesül:
     *      a teleporter homeAsteroid tagváltozóját ráállítja az aszteroidára amin a telepes áll,
     *      a  jelen aszteroida Teleporter tagváltozóját beállítja a teleporterre,
     *      eltávolítja a teleportert a játékos inventoryjából</p>
     */
    @Override
    public void PlaceTeleporter() {
        Logger.Log(this, "Place teleporter");

        TeleportGate storageTeleporter = storage.GetOneTeleporter();
        TeleportGate asteroidTeleporter = onAsteroid.GetTeleporter();

        if (storageTeleporter != null && asteroidTeleporter == null) {

            storageTeleporter.SetHomeAsteroid(onAsteroid);
            onAsteroid.SetTeleporter(storageTeleporter);

            storage.RemoveTeleportGate(storageTeleporter);

        }

        Logger.Return();
    }

    /**
     * A telepes megpróbál lerakni egy nyersanyagot egy üres aszteroidára
     * <p>ellenőrzi üres-e az aszteroida magja.
     *    Csak akkor sikerül ha az aszteroida üres</p>
     * @param abstractBaseResource a storage-ből kiválasztott nyersanyag
     */
    @Override
    public void PlaceResource(AbstractBaseResource abstractBaseResource) {

        Logger.Log(this, "Placing resource " + Logger.GetName(abstractBaseResource));
        if(onAsteroid.GetResource()==null) {
            onAsteroid.SetResource(abstractBaseResource);
        }
        Logger.Return();
    }
}
