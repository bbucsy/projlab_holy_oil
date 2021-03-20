package hu.holyoil.collection;

import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * nyersanyagokat listázó osztály. Nem összekeverendő a PlayerStorage-al.
 */
public class BillOfMaterial {
    /**
     * 
     */
    List<AbstractBaseResource> resources;

    public BillOfMaterial() {
        resources = new ArrayList<>();
    }

    public void AddMaterial(AbstractBaseResource abstractBaseResource){
        Logger.Log(this,"Adding Material: " + Logger.GetName(abstractBaseResource));
        resources.add(abstractBaseResource);
        Logger.Return();
    }

    public List<AbstractBaseResource> GetMaterials() {
        Logger.Log(this,"Returning materials");
        Logger.Return();
        return resources;
    }
}
