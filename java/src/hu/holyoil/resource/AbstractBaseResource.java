package hu.holyoil.resource;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.storage.PlayerStorage;

public abstract class AbstractBaseResource {

    public void ReactToMine(Asteroid asteroid, IStorageCapable iStorageCapable) {
        System.out.println("I am " + this.toString() + " and I am being mined by " + iStorageCapable.toString() + " on " + asteroid.toString());
        BillOfMaterial billOfMaterial = new BillOfMaterial();
        billOfMaterial.AddMaterial(this);
        PlayerStorage storage = iStorageCapable.GetStorage();
        if (storage.GetSumResources() < 10) {
            storage.AddBill(
                    billOfMaterial
            );
            asteroid.SetResource(null);
        }
    }
    public void ReactToPlace(Asteroid asteroid, IStorageCapable iStorageCapable) {
        System.out.println("I am " + this.toString() + " and I am being placed by " + iStorageCapable.toString() + " on " + asteroid.toString());
        BillOfMaterial billOfMaterial = new BillOfMaterial();
        billOfMaterial.AddMaterial(this);
        if (iStorageCapable.GetStorage().HasEnoughOf(billOfMaterial)) {
            iStorageCapable.GetStorage().RemoveBill(billOfMaterial);
            asteroid.SetResource(this);
        }
    }
    public abstract Boolean IsSameType(AbstractBaseResource abstractBaseResource);

    public void ReactToSunNearby(Asteroid asteroid) {
        System.out.println("I am resource " + this.toString() + " and I am now nearby a sun");
    }

}
