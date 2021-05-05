package hu.holyoil.storage;

import hu.holyoil.IIdentifiable;
import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.repository.PlayerStorageBaseRepository;
import hu.holyoil.resource.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A játékosok inventoryját leíró osztály.
 */
public class PlayerStorage implements IIdentifiable {
    /**
     * Paraméter nélküli konstruktor.
     * Inicializálja a tagváltozó listákat.
     */
    public PlayerStorage() {
        this(PlayerStorageBaseRepository.GetIdWithPrefix("PlayerStorage"));
    }

    public PlayerStorage(String name) {

        storedMaterials = new ArrayList<>();
        teleporters = new ArrayList<>();
        id = name;
        PlayerStorageBaseRepository.GetInstance().Add(name, this);
    }

    /**
     * A tároló egyedi azonosítója
     * */
    private String id;

    /**
     * Visszaadja a storage egyedi azonosítóját
     * */
    public String GetId() {
        return id;
    }

    /**
     * Kiírja a storage-ot emberileg olvasható formátumban. Az asszociációk helyére ID-k kerülnek.
     * */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(
                "PLAYERSTORAGE (name:) " + id
        );

        stringBuilder.append("\n\t(material names:) [");
        for (int i = 0; i < storedMaterials.size(); i++) {
            stringBuilder.append(storedMaterials.get(i).GetId());
            if (i != storedMaterials.size() - 1) {
                stringBuilder.append(" ");
            }
        }
        stringBuilder.append("]\n\t(teleporter names:) [");
        for (int i = 0; i < teleporters.size(); i++) {
            stringBuilder.append(teleporters.get(i).GetId());
            if (i != teleporters.size() - 1) {
                stringBuilder.append(" ");
            }
        }
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    /**
     * A teleportereket tartalmazó lista. Egy játékosnál maximum kettő teleporter lehet.
     */
    private List<TeleportGate> teleporters;

    /**
     * Egyszerű getter a tárolt anyagok listájához
     * @return tárolt anyagok
     */
    public List<AbstractBaseResource> GetStoredMaterials() {
        return storedMaterials;
    }

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
        teleportGate1.SetHomeStorage(this);
        teleporters.add(teleportGate1);
        teleportGate2.SetHomeStorage(this);
        teleporters.add(teleportGate2);
    }

    public void AddTeleportGate(TeleportGate tp) {

        teleporters.add(tp);

    }

    /**
     * Visszaadja, hogy jelenleg hány teleportert tárol ez a tároló.
     * @return Tárolt teleporterek száma.
     * */
    public int GetTeleporterCount() {
        return teleporters.size();
    }

    /**
     * Elvátolítja a benne lévő objektumokat a játéktérből.
     * */
    public void ReactToSettlerDie() {
        storedMaterials.forEach(AbstractBaseResource::ReactToGettingDestroyed);
        List<TeleportGate> teleportGatesShallowCopy = new ArrayList<>(teleporters);
        teleportGatesShallowCopy.forEach(TeleportGate::Explode);
        PlayerStorageBaseRepository.GetInstance().Remove(id);
    }

    /**
     * Eltávolít egy teleportert a tárolóból.
     * @param teleportGate az eltávolítandó teleporter
     */
    public void RemoveTeleportGate(TeleportGate teleportGate) {
        teleporters.remove(teleportGate);
        teleportGate.SetHomeStorage(null);
    }

    /**
     * Visszaad egy teleportert a tárolóból.
     * <p>
     *     Ez lehet null, ha nincs a telepesnél egy darab se.
     * </p>
     * @return Az első teleporter a listából
     */
    public TeleportGate GetOneTeleporter() {
        if (teleporters.size() == 0) {
            return null;
        } else {
            return teleporters.get(0);
        }
    }

    /**
     * Visszaadja hány nyersanyagot tárol jelenleg.
     * @return a nyersanyag lista mérete
     */
    public Integer GetSumResources() {
        return storedMaterials.size();
    }

    /**
     * Hozzáad egy billnyi nyersanyagot a tárolóhoz.
     * @param billOfMaterial Az ebben lévő resource-okat adja hozzá a storage-hoz.
     */
    public void AddBill(BillOfMaterial billOfMaterial) {
        storedMaterials.addAll(billOfMaterial.GetMaterials());
    }

    /**
     * Eltávolít egy billnyi nyersanyagot a tárolóból.
     * @param billOfMaterial az eltávolítandó nyersanyagok billje
     */
    public void RemoveBill(BillOfMaterial billOfMaterial) {

        RemoveBill(billOfMaterial, false);

    }

    public void RemoveBill(BillOfMaterial billOfMaterial, boolean deleteMaterials) {
        storedMaterials.removeAll(billOfMaterial.GetMaterials());

        for (AbstractBaseResource billResource : billOfMaterial.GetMaterials()) {

            for (AbstractBaseResource storedResource : storedMaterials) {

                if (storedResource.IsSameType(billResource)) {

                    storedMaterials.remove(
                            storedResource
                    );

                    if (deleteMaterials) {
                        storedResource.ReactToGettingDestroyed();
                    }

                    break;

                }

            }

        }
    }

    /**
     * Eldönti van-e elég nyersanyag egy bizonyos összeállításból.
     * <p>
     *     Például recepteknél használatos.
     * </p>
     * @param billOfMaterial Az ebben lévő dolgokat ellenőrzi le
     * @return Van-e elég a billOfMaterial-ban lévő anyagokból
     */
    public Boolean HasEnoughOf(BillOfMaterial billOfMaterial) {
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
