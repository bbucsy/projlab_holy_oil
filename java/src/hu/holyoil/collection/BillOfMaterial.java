package hu.holyoil.collection;

import hu.holyoil.resource.AbstractBaseResource;

import java.util.ArrayList;
import java.util.List;

/**
 * nyersanyagokat listázó osztály. Nem összekeverendő a PlayerStorage entitással. Elsősorban a receptek kezelésére használjuk.
 */
public class BillOfMaterial {
    /**
     * nyersanyagok listája
     */
    List<AbstractBaseResource> resources;

    /**
     * paraméter nélküli konstruktor
     * <p>inicializálja a resources listát (üres)</p>
     */
    public BillOfMaterial() {
        resources = new ArrayList<>();
    }

    /**
     * Hozzzáad egy nyersanyagot a billhez
     * @param abstractBaseResource a hozzáadandó nyersanyag
     */
    public void AddMaterial(AbstractBaseResource abstractBaseResource) {
        resources.add(abstractBaseResource);
    }

    /**
     * Visszaadja a felvett nyersanyagok listáját
     * @return a nyersanyagok listája
     */
    public List<AbstractBaseResource> GetMaterials() {
        return resources;
    }
}
