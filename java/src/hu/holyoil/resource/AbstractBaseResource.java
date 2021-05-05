package hu.holyoil.resource;

import hu.holyoil.IIdentifiable;
import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.controller.Logger;
import hu.holyoil.crewmate.IMiner;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.ResourceBaseRepository;
import hu.holyoil.storage.PlayerStorage;

import java.awt.*;

/**
 * A nyersanyagokat általánosító absztrakt osztály.
 * Nem példányosítható.
 */
public abstract class AbstractBaseResource implements IIdentifiable {

    /**
     * A nyersanyag egyedi azonosítója
     * */
    protected String id;

    /**
     * Visszaadja a nyersanyag egyedi azonosítóját
     * */
    public String GetId() {
        return id;
    }

    /**
     * Az aszteroidából való bányászást kezeli tároló nélküli bányász esetén.
     * <p>
     *     Az aszteroida magja üressé válik.
     * </p>
     * @param asteroid a nyersanyagot tartalmazó aszteroida
     * @param iMiner a bányászást megkísérlő ufó
     */
    public void ReactToMine(Asteroid asteroid, IMiner iMiner) {
        asteroid.SetResource(null);
        iMiner.ReactToMoveMade();
        ReactToGettingDestroyed();
        Logger.Log(this,"got mined by a ufo");
    }

    /**
     * Az aszteroidából való bányászást kezeli tárolóval rendelkező bányász esetén.
     * <p>
     *     Létrehoz egy új billt, ehhez hozzáadja magát. Elkéri a bányász inventoryját. Ha még nincs tele az inventory hozzáadja a billt.
     *     Az aszteroida magja üressé válik.
     * </p>
     * @param asteroid a nyersanyagot tartalmazó aszteroida
     * @param iMiner a bányászást megkísérlő telepes
     * @param storage a telepes tárolója
     */
    public void ReactToMine(Asteroid asteroid, IMiner iMiner, PlayerStorage storage) {
        BillOfMaterial billOfMaterial = new BillOfMaterial();
        billOfMaterial.AddMaterial(this);
        if (storage.GetSumResources() < 10) {
            storage.AddBill(
                    billOfMaterial
            );
            asteroid.SetResource(null);
            iMiner.ReactToMoveMade();
        }
    }

    /**
     * Reagál arra, hogy egy telepes feltölt egy aszteroidát vele,
     * <p>
     *     Ha a telepesnél van egy darab belőle, levonja magát a tárolóból, és feltölti az aszteroida magját magával.
     *     Az aszteroida üreges volta már le van ellenőrizve amikor ez a metódus hívódik.
     * </p>
     * @param asteroid az üres célaszteroida
     * @param iStorageCapable a tárolója a telepesnek aki le akarja tenni a nyersanyagot
     */
    public void ReactToPlace(Asteroid asteroid, IStorageCapable iStorageCapable) {
        BillOfMaterial billOfMaterial = new BillOfMaterial();
        billOfMaterial.AddMaterial(this);

        if (iStorageCapable.GetStorage().HasEnoughOf(billOfMaterial)) {
            iStorageCapable.GetStorage().RemoveBill(billOfMaterial);
            asteroid.SetResource(this);
            iStorageCapable.ReactToMoveMade();
        }
    }

    /**
     * Igazzal tér vissza, ha a paraméter nyersanyag ugyan olyan típusú.
     * Minden nyersanyagnak realizálnia kell.
     * @param abstractBaseResource az összehasonlítandó nyersanyag
     * @return igaz/hamis: igaz, ha ugyan olyanok, hamis, ha különbözőek
     */
    public abstract Boolean IsSameType(AbstractBaseResource abstractBaseResource);

    /**
     * Reagál napközelre.
     * Alapból nem csinál semmit. Ha egy nyersanyagnak valahogy reagálnia kell a napközelre, ezt felül kell írnia.
     * @param asteroid az aszteroida amiben a nyersanyag megtalálható
     */
    public void ReactToSunNearby(Asteroid asteroid) {
        // do nothing by default
    }

    public void ReactToGettingDestroyed() {
        ResourceBaseRepository.GetInstance().Remove(id);
    }

    /**
     * Visszaadja az ikonját
     */
    public abstract Image GetImage();
}
