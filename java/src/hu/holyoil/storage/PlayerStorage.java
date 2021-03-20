package hu.holyoil.storage;

import hu.holyoil.Main;
import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;

public class PlayerStorage {

    public PlayerStorage() {
        storedMaterials = new ArrayList<>();
        teleporters = new ArrayList<>();
    }

    private List<TeleportGate> teleporters;
    protected List<AbstractBaseResource> storedMaterials;

    public void AddTeleportGatePair(TeleportGate teleportGate1, TeleportGate teleportGate2) {

        Logger.Log(this, "Adding teleportgate pair of " + Logger.GetName(teleportGate1) + " and " + Logger.GetName(teleportGate2));
        teleporters.add(teleportGate1);
        teleporters.add(teleportGate2);
        Logger.Return();

    }

    public void RemoveTeleportGate(TeleportGate teleportGate) {

        Logger.Log(this, "Removing teleportgate " + Logger.GetName(teleportGate));
        teleporters.remove(teleportGate);
        Logger.Return();

    }

    public TeleportGate GetOneTeleporter() {

        Logger.Log(this, "Returning one or no teleporter");
        if (teleporters.size() == 0) {
            Logger.Return();
            return null;
        } else {
            Logger.Return();
            return teleporters.get(0);
        }

    }

    public Integer GetSumResources() {

        Logger.Log(this, "Returning sum resources");
        Logger.Return();
        return storedMaterials.size();

    }

    public void AddBill(BillOfMaterial billOfMaterial) {

        Logger.Log(this, "Adding bill " + Logger.GetName(billOfMaterial));
        storedMaterials.addAll(billOfMaterial.GetMaterials());
        Logger.Return();

    }

    public void RemoveBill(BillOfMaterial billOfMaterial) {

        Logger.Log(this, "Removing bill " + Logger.GetName(billOfMaterial));

        storedMaterials.removeAll(billOfMaterial.GetMaterials());

        for (AbstractBaseResource billResource : billOfMaterial.GetMaterials()) {

            for (AbstractBaseResource storedResource : storedMaterials) {

                if (storedResource.IsSameType(billResource)) {

                    storedMaterials.remove(
                            storedResource
                    );

                    break;

                }

            }

        }

        Logger.Return();

    }

    public Boolean HasEnoughOf(BillOfMaterial billOfMaterial) {

        Logger.Log(this, "Checking if I have enough of " + Logger.GetName(billOfMaterial));
        Logger.Return();

        boolean[] checked = new boolean[storedMaterials.size()];
        for (int i = 0; i < storedMaterials.size(); i++)
            checked[i] = false;

        for (AbstractBaseResource billResource : billOfMaterial.GetMaterials()) {

            boolean found = false;

            for (int i = 0; i < storedMaterials.size(); i++) {

                if (storedMaterials.get(i).IsSameType(billResource) && !checked[i]) {
                    checked[i] = true;
                    found = true;
                    break;
                }

            }

            if (!found) {
                return false;
            }

        }

        return true;

    }

}
