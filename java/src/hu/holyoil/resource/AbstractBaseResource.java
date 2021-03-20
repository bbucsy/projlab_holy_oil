package hu.holyoil.resource;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.storage.PlayerStorage;

public abstract class AbstractBaseResource {

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
    public abstract Boolean IsSameType(AbstractBaseResource abstractBaseResource);

    public void ReactToSunNearby(Asteroid asteroid) {
        Logger.Log(this,"Reacting to nearby sun");
        Logger.Return();
    }

}
