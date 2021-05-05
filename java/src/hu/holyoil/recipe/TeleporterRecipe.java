package hu.holyoil.recipe;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.repository.NeighbourBaseRepository;
import hu.holyoil.repository.ResourceBaseRepository;
import hu.holyoil.resource.Iron;
import hu.holyoil.resource.Uranium;
import hu.holyoil.resource.Water;
import hu.holyoil.storage.PlayerStorage;

/**
 * A teleporterek gyártásáért felelős singleton osztály,
 * nem lehet példányosítani
 */
public class TeleporterRecipe implements IRecipe {
    /**
     * a singleton volta miatt kell
     */
    private static TeleporterRecipe teleporterRecipe;

    /**
     * megvalósítja a teleportkapuk gyártását
     * <p>
     *     a teleportkapukat csak párosával lehet olyan telepesnek létrehoznia akinél maximum egy teleporter van
     * </p>
     * <p>létrehozza a szükséges nyersanyagokat, majd ezeket beleteszi a billbe.
     *         Elkéri a játékos tárolóját, és ellenőrzi megvan-e minden a billről
     * </p>
     * <p>
     *     Ellenőrzi a telepes tárolóját
     * </p>
     * <p>
     *     Ha van hely még kettő teleporternek ÉS van nála elég anyag akkor:
     *
     * eltávolítja a felhasználandó nyersanyagokat,
     * létrehoz két teleporter kaput,
     * ezeket beállítja egymás párjainak (mind a kettőn elvégzendő),
     * hozzáadja a két teleportert a telepes tárolójába.</p>
     * @param iStorageCapable a gyártást végrehajtó telepes
     *                        (a tárolója tartalma miatt át kell adni, majd később ide kerülnek a teleporterek)
     * @param asteroid az aszteroida amin a gyártás megtörténik
     */
    @Override
    public void Craft(IStorageCapable iStorageCapable, Asteroid asteroid) {

        BillOfMaterial billOfMaterial = new BillOfMaterial();
        Iron iron1 = new Iron(
                ResourceBaseRepository.GetIdWithPrefix("Iron")
        );
        Iron iron2 = new Iron(
                ResourceBaseRepository.GetIdWithPrefix("Iron")
        );
        Uranium uranium = new Uranium(
                ResourceBaseRepository.GetIdWithPrefix("Uranium")
        );
        Water water = new Water(
                ResourceBaseRepository.GetIdWithPrefix("Water")
        );
        billOfMaterial.AddMaterial(iron1);
        billOfMaterial.AddMaterial(iron2);
        billOfMaterial.AddMaterial(uranium);
        billOfMaterial.AddMaterial(water);
        PlayerStorage storage = iStorageCapable.GetStorage();

        if (storage.HasEnoughOf(billOfMaterial) && storage.GetTeleporterCount() <= 1) {

            storage.RemoveBill(billOfMaterial, true);
            TeleportGate t1 = new TeleportGate(
                    NeighbourBaseRepository.GetIdWithPrefix("t")
            ), t2 = new TeleportGate(
                    NeighbourBaseRepository.GetIdWithPrefix("t")
            );
            t1.SetPair(t2);
            t2.SetPair(t1);
            storage.AddTeleportGatePair(t1, t2);
            iStorageCapable.ReactToMoveMade();

        }

        ResourceBaseRepository.GetInstance().Remove(iron1.GetId());
        ResourceBaseRepository.GetInstance().Remove(iron2.GetId());
        ResourceBaseRepository.GetInstance().Remove(water.GetId());
        ResourceBaseRepository.GetInstance().Remove(uranium.GetId());

    }

    /**
     * Singleton osztályra való hivatkozás
     * @return visszaad egy instance-ot
     */
    public static TeleporterRecipe GetInstance() {

        if (teleporterRecipe == null) {
            teleporterRecipe = new TeleporterRecipe();
        }

        return teleporterRecipe;

    }

    /**
     * Privát konstruktor: nem lehet meghívni, mivel nem lehet példányosítani
     */
    private TeleporterRecipe() {}

}
