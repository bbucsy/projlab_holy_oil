package hu.holyoil.storage;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.resource.AbstractBaseResource;

import java.util.List;

public class PlayerStorage {

    private List<TeleportGate> teleporters;
    protected List<AbstractBaseResource> storedMaterials;

    public void AddTeleportGatePair(TeleportGate teleportGate1, TeleportGate teleportGate2) {
        System.out.println("I am storage " + this.toString() + ", teleportgates " + teleportGate1.toString() + " and " + teleportGate2.toString() + " are being added to me");
    }

    public void RemoveTeleportGate(TeleportGate teleportGate) {
        System.out.println("I am storage " + this.toString() + " and I am removing " + teleportGate.toString());
    }

    public TeleportGate GetOneTeleporter() {
        System.out.println("I am storage " + this.toString() + " and someone wants one teleporter");
        return null;
    }

    public Integer GetSumResources() {
        System.out.println("I am storage " + this.toString() + " and someone wants the number of resources I have");
        return 0;
    }

    public void AddBill(BillOfMaterial billOfMaterial) {
        System.out.println("I am storage " + this.toString() + " and this bill is added to me: " + billOfMaterial.toString());
    }

    public void RemoveBill(BillOfMaterial billOfMaterial) {
        System.out.println("I am storage " + this.toString() + " and this bill is removed from me: " + billOfMaterial.toString());
    }

    public Boolean HasEnoughOf(BillOfMaterial billOfMaterial) {
        System.out.println("I am storage " + this.toString() + " and someone is asking if i have enough of: " + billOfMaterial.toString());
        return Boolean.FALSE;
    }

}
