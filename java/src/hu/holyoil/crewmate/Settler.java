package hu.holyoil.crewmate;

import hu.holyoil.controller.TurnController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.recipe.RobotRecipe;
import hu.holyoil.recipe.TeleporterRecipe;
import hu.holyoil.repository.PlayerStorageBaseRepository;
import hu.holyoil.repository.SpaceshipBaseRepository;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.storage.PlayerStorage;

import javax.swing.*;
import java.awt.*;

/**
 * A Telepeseket leíró osztály.
 * Leszármazottja az AbstractCrewmate-nek (robottal közös tulajdonságai miatt).
 * Implementálja az IStorageCapable-t mert képes tárolásra és gyártásra.
 */
public class Settler extends AbstractCrewmate implements IStorageCapable, IMiner {
    /**
     * Statikus, minden telepesre jellemző ikon
     */
    protected static Image image = new ImageIcon("assets/settler.gif").getImage();

    /**
     * Kiírja a settler-t emberileg olvasható módon. Az asszociációk helyén id-ket írunk ki.
     * */
    @Override
    public String toString() {
        return "SETTLER (name:) " + id + "\n\t(asteroid name:) " + onAsteroid.GetId() + "\n\t(storage name:) " + storage.GetId();
    }

    /**
     * Settler konstruktora
     * <p>inicializálja a storage-ot,
     * hozzáadja az aszteroidához a telepest</p>
     * @param startingAsteroid Aszteroida amelyen létrehozza a settlert
     *
     */
    public Settler(Asteroid startingAsteroid) {
        this(startingAsteroid, SpaceshipBaseRepository.GetIdWithPrefix("Settler"), null);
    }

    public Settler(Asteroid asteroid, String name) {
        this(asteroid, name, null);
    }

    public Settler(Asteroid asteroid, String name, String storageName) {
        id = name;
        if (storageName == null) {
            storage = new PlayerStorage();
        }
        else {
            storage = new PlayerStorage(storageName);
        }
        onAsteroid = asteroid;

        SpaceshipBaseRepository.GetInstance().Add(name, this);
        TurnController.GetInstance().RegisterEntityWithAction(this);
        onAsteroid.AddSpaceship(this);
    }



    /**
     * A telepes saját inventoryja
     */
    private PlayerStorage storage;

    public void DestroyStorage() {

        PlayerStorageBaseRepository.GetInstance().Remove(storage.GetId());
        storage = null;

    }

    /**
     * A telepes meghal
     * <p>felülírja az AbstractCrewmate Die() metódusát.
     * Eltávolítja a telepest a GameControllerből.
     * Ha a telepesnél van legalább egy teleporter azt felrobbantja (ami felrobbantja a párját).
     * Az aszteroidáról is kitörli a telepest</p>
     */
    @Override
    public void Die() {
        if (storage.GetOneTeleporter() != null) {
            storage.GetOneTeleporter().Explode();
        }

        SpaceshipBaseRepository.GetInstance().Remove(id);

        storage.ReactToSettlerDie();

        onAsteroid.RemoveSpaceship(this);

        TurnController.GetInstance().RemoveEntityWithAction(this);
    }

    /**
     * Az aszteroida által meghívandó függvény, ha az aszteroida felrobban.
     * <p>Meghívja a Die() függvényt.</p>
     */
    @Override
    public void ReactToAsteroidExplosion() {
        Die();
    }

    /**
     * Robot készítése
     * <p>a tényleges robot gyártás és feltétel ellenőrzést a RobotRecipe végzi.
     * Meghívja a RobotRecipe() singleton osztály Craft() metódusát</p>
     */
    @Override
    public void CraftRobot() {
        if (TurnController.GetInstance().HasNoActionsLeft(this)) {
            return;
        }

        RobotRecipe.GetInstance().Craft(this, onAsteroid);
    }

    /**
     *Teleportkapu pár gyártása (mindig egyszerre kettőt)
     * <p>A tényleges teleport gyártás és feltétel ellenőrzést a TeleporterRecipe végzi.
     * Meghívja a TeleporterRecipe() singleton osztály Craft() metódusát</p>
     */
    @Override
    public void CraftTeleportGate() {
        if (TurnController.GetInstance().HasNoActionsLeft(this)) {
            return;
        }

        TeleporterRecipe.GetInstance().Craft(this, onAsteroid);
    }

    /**
     * A telepes megpróbálja kibányászni a jelen aszteroida nyersanyagát
     * <p>Az aszteroida ReactToMineBy metódusa meghívja az AbstractBaseResource ReactToMine metódusát.
     * Az AbstractBaseResource kezeli le az aszteroida ürítését és az inventory feltöltését</p>
     */
    @Override
    public void Mine() {
        if (TurnController.GetInstance().HasNoActionsLeft(this)) {
            return;
        }

        onAsteroid.ReactToMineBy(this, storage);
    }

    /**
     * @return visszatér a játékos inventoryjával a jelen állapotában
     */
    @Override
    public PlayerStorage GetStorage() {
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

        if (TurnController.GetInstance().HasNoActionsLeft(this)) {

            return;

        }


        TeleportGate storageTeleporter = storage.GetOneTeleporter();
        TeleportGate asteroidTeleporter = onAsteroid.GetTeleporter();

        if (storageTeleporter != null && asteroidTeleporter == null) {

            storageTeleporter.SetHomeAsteroid(onAsteroid);
            onAsteroid.SetTeleporter(storageTeleporter);

            storage.RemoveTeleportGate(storageTeleporter);
            ReactToMoveMade();

        }
    }

    /**
     * A telepes megpróbál lerakni egy nyersanyagot egy üres aszteroidára
     * <p>ellenőrzi üres-e az aszteroida magja.
     *    Csak akkor sikerül ha az aszteroida üres</p>
     * @param abstractBaseResource a storage-ből kiválasztott nyersanyag
     */
    @Override
    public void PlaceResource(AbstractBaseResource abstractBaseResource) {

        if (TurnController.GetInstance().HasNoActionsLeft(this)) {

            return;

        }

        if(onAsteroid.GetResource()==null)
             onAsteroid.PutResource(this, abstractBaseResource);
    }

    public void SetStorage(PlayerStorage playerStorage) {

        storage = playerStorage;

    }

    /**
     * Visszaadja az ikonját
     */
    @Override
    public Image GetImage() {
        return image;
    }

    /**
     * Végrehajtott egy műveletet.
     * */
    public void ReactToMoveMade() {
        TurnController.GetInstance().ReactToActionMade(this);
    }
}
