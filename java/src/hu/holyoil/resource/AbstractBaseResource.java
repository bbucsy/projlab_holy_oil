package hu.holyoil.resource;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.storage.PlayerStorage;

/**
 * A nyersanyagokat általánosító absztrakt osztály.
 * Nem példányosítható.
 */
public abstract class AbstractBaseResource {
    /**
     * Az aszteroidából való bányászást kezeli.
     * <p>
     *     Létrehoz egy új billt, ehhez hozzáadja magát. Elkéri a bányász inventoryját. Ha még nincs tele az inventory hozzáadja a billt.
     *     Az aszteroida magja üressé válik.
     * </p>
     * @param asteroid a nyersanyagot tartalmazó aszteroida
     * @param iStorageCapable a bányászást megkísérlő telepes
     */
    public void ReactToMine(Asteroid asteroid, IStorageCapable iStorageCapable) {
        Logger.Log(this,"ReactingToMine by " + Logger.GetName(iStorageCapable));

        BillOfMaterial billOfMaterial = new BillOfMaterial();
        Logger.RegisterObject(billOfMaterial, "billOfMaterial: BillOfMaterial");
        billOfMaterial.AddMaterial(this);
        PlayerStorage storage = iStorageCapable.GetStorage();
        if (storage.GetSumResources() < 10) {
            storage.AddBill(
                    billOfMaterial
            );
            asteroid.SetResource(null);
        }

        Logger.Return();
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
        Logger.Log(this ,"Reacting to Place by " + Logger.GetName(iStorageCapable));

        BillOfMaterial billOfMaterial = new BillOfMaterial();
        Logger.RegisterObject(billOfMaterial, "billOfMaterial: BillOfMaterial");

        billOfMaterial.AddMaterial(this);

        if (iStorageCapable.GetStorage().HasEnoughOf(billOfMaterial)) {
            iStorageCapable.GetStorage().RemoveBill(billOfMaterial);
            asteroid.SetResource(this);
        }

        Logger.Return();
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
        Logger.Log(this,"Reacting to nearby sun");
        Logger.Return();
    }

}
