package hu.holyoil.storage;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.resource.AbstractBaseResource;

import java.util.ArrayList;
import java.util.List;

public class PlayerStorage {

    private static Integer ID = 0;
    private Integer myID;

    public PlayerStorage() {
        myID = ID;
        ID++;
        storedMaterials = new ArrayList<>();
        teleporters = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "playerstorage " + myID.toString();
    }

    private List<TeleportGate> teleporters;
    protected List<AbstractBaseResource> storedMaterials;

    public void AddTeleportGatePair(TeleportGate teleportGate1, TeleportGate teleportGate2) {
        System.out.println("I am storage " + this.toString() + ", teleportgates " + teleportGate1.toString() + " and " + teleportGate2.toString() + " are being added to me");
        teleporters.add(teleportGate1);
        teleporters.add(teleportGate2);
    }

    public void RemoveTeleportGate(TeleportGate teleportGate) {
        System.out.println("I am storage " + this.toString() + " and I am removing " + teleportGate.toString());
        teleporters.remove(teleportGate);
    }

    public TeleportGate GetOneTeleporter() {
        System.out.println("I am storage " + this.toString() + " and someone wants one teleporter");
        if (teleporters.size() == 0) {
            return null;
        } else {
            return teleporters.get(0);
        }
    }

    public Integer GetSumResources() {
        System.out.println("I am storage " + this.toString() + " and someone wants the number of resources I have");
        return storedMaterials.size();
    }

    public void AddBill(BillOfMaterial billOfMaterial) {
        System.out.println("I am storage " + this.toString() + " and this bill is added to me: " + billOfMaterial.toString());
        storedMaterials.addAll(billOfMaterial.GetMaterials());
    }

    public void RemoveBill(BillOfMaterial billOfMaterial) {
        System.out.println("I am storage " + this.toString() + " and this bill is removed from me: " + billOfMaterial.toString());
        storedMaterials.removeAll(billOfMaterial.GetMaterials());
    }

    public Boolean HasEnoughOf(BillOfMaterial billOfMaterial) {
        System.out.println("I am storage " + this.toString() + " and someone is asking if i have enough of: " + billOfMaterial.toString());
        // Logic
        return Boolean.TRUE;
    }

}
