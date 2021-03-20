package hu.holyoil.storage;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A játékosok inventoryját leíró osztály.
 */
public class PlayerStorage {
    /**
     * Paraméter nélküli konstruktor.
     * Inicializálja a tagváltozó listákat.
     */
    public PlayerStorage() {
        storedMaterials = new ArrayList<>();
        teleporters = new ArrayList<>();
    }

    /**
     * A teleportereket tartalmazó lista. Egy játékosnál maximum kettő teleporter lehet.
     */
    private List<TeleportGate> teleporters;
    /**
     * A tárolt nyersanyagok listája. Egy játékosnál egyszerre 10 nyersanyag lehet.
     */
    protected List<AbstractBaseResource> storedMaterials;

    /**
     * Hozzáad a tárolóhoz két teleportert amik egymásnak párjai.
     * <p>
     *     Mivel egyszerre hozzuk létre a párokat, egyszerre tesszük be.
     * </p>
     * @param teleportGate1 a pár egyik fele
     * @param teleportGate2 a pár másik fele
     */
    public void AddTeleportGatePair(TeleportGate teleportGate1, TeleportGate teleportGate2) {

        Logger.Log(this, "Adding teleportgate pair of " + Logger.GetName(teleportGate1) + " and " + Logger.GetName(teleportGate2));
        teleportGate1.SetHomeStorage(this);
        teleporters.add(teleportGate1);
        teleportGate2.SetHomeStorage(this);
        teleporters.add(teleportGate2);
        Logger.Return();

    }

    /**
     * Eltávolít egy teleportert a tárolóból.
     * @param teleportGate az eltávolítandó teleporter
     */
    public void RemoveTeleportGate(TeleportGate teleportGate) {

        Logger.Log(this, "Removing teleportgate " + Logger.GetName(teleportGate));
        teleporters.remove(teleportGate);
        teleportGate.SetHomeStorage(null);
        Logger.Return();

    }

    /**
     * Visszaad egy teleportert a tárolóból.
     * <p>
     *     Ez lehet null, ha nincs a telepesnél egy darab se.
     * </p>
     * @return
     */
    public TeleportGate GetOneTeleporter() {

        if (teleporters.size() == 0) {
            Logger.Log(this, "Returning no teleporter");
            Logger.Return();
            return null;
        } else {
            Logger.Log(this, "Returning one teleporter");
            Logger.Return();
            return teleporters.get(0);
        }

    }

    /**
     * Visszaadja hány nyersanyagot tárol jelenleg.
     * @return a nyersanyag lista mérete
     */
    public Integer GetSumResources() {

        Logger.Log(this, "Returning sum resources");
        Logger.Return();
        return storedMaterials.size();

    }

    /**
     * Hozzáad egy billnyi nyersanyagot a tárolóhoz.
     * @param billOfMaterial
     */
    public void AddBill(BillOfMaterial billOfMaterial) {

        Logger.Log(this, "Adding bill " + Logger.GetName(billOfMaterial));
        storedMaterials.addAll(billOfMaterial.GetMaterials());
        Logger.Return();

    }

    /**
     * Eltávolít egy billnyi nyersanyagot a tárolóból.
     * @param billOfMaterial az eltávolítandó nyersanyagok billje
     */
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

    /**
     * Eldönti van-e elég nyersanyag egy bizonyos összeállításból.
     * <p>
     *     Például recepteknél használatos.
     * </p>
     * @param billOfMaterial
     * @return
     */
    public Boolean HasEnoughOf(BillOfMaterial billOfMaterial) {

        Logger.Log(this, "Checking if I have enough of " + Logger.GetName(billOfMaterial));

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
                Logger.Log(this, "I do not have enough to complete this bill");
                Logger.Return();
                Logger.Return();
                return false;
            }

        }

        Logger.Log(this, "I have enough to complete this bill");
        Logger.Return();
        Logger.Return();
        return true;

    }

}
