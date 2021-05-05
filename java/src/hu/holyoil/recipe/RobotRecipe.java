package hu.holyoil.recipe;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.ResourceBaseRepository;
import hu.holyoil.resource.Coal;
import hu.holyoil.resource.Iron;
import hu.holyoil.resource.Uranium;
import hu.holyoil.storage.PlayerStorage;

/**
 * A robot gyártásáért felelős singleton osztály,
 * nem lehet példányosítani
 */
public class RobotRecipe implements IRecipe {
    /**
     * ez azért kell mert singleton
     */
    private static RobotRecipe robotRecipe;

    /**
     * megvalósítja a robot gyártását
     * <p> létrehoz egy új resource listát ami kelleni fog a gyártáshoz
     *                       (BillOfMaterial)</p>
     * <p>létrehozza a szükséges nyersanyagokat, majd ezeket beleteszi a billbe.
     *       Elkéri a játékos tárolóját, és ellenőrzi megvan-e minden a billről</p>
     * <p>Ha van:</p>
     *                       kiveszi a tárolóból a gyártáshoz felhasznált anyagokat,
     *                       létrehoz egy robotot a jelen aszteroidán,
     *                       az aszteroidához hozzáadja a robotot,
     *                       a robotot hozzáadja a RobotControllerhez.
     * @param iStorageCapable a gyártást végrehajtó telepes
     *                        (a tárolója tartalma miatt át kell adni)
     * @param asteroid az aszteroida amin a gyártás megtörténik
     */
    @Override
    public void Craft(IStorageCapable iStorageCapable, Asteroid asteroid) {

        BillOfMaterial billOfMaterial = new BillOfMaterial();

        Iron iron = new Iron(
                ResourceBaseRepository.GetIdWithPrefix("Iron")
        );

        Uranium uranium = new Uranium(
                ResourceBaseRepository.GetIdWithPrefix("Uranium")
        );

        Coal coal = new Coal(
                ResourceBaseRepository.GetIdWithPrefix("Coal")
        );

        billOfMaterial.AddMaterial(iron);
        billOfMaterial.AddMaterial(uranium);
        billOfMaterial.AddMaterial(coal);
        PlayerStorage storage = iStorageCapable.GetStorage();

        if (storage.HasEnoughOf(billOfMaterial)) {

            storage.RemoveBill(billOfMaterial, true);
            Robot robot = new Robot(asteroid);
            iStorageCapable.ReactToMoveMade();

        }

        ResourceBaseRepository.GetInstance().Remove(iron.GetId());
        ResourceBaseRepository.GetInstance().Remove(uranium.GetId());
        ResourceBaseRepository.GetInstance().Remove(coal.GetId());

    }

    /**
     * Singleton osztályként így lehet rá referálni
     * @return visszaad egy instance-ot
     */
    public static RobotRecipe GetInstance() {

        if (robotRecipe == null) {
            robotRecipe = new RobotRecipe();
        }

        return robotRecipe;

    }

    /**
     * Privát konstruktor,
     * nem lehet meghívni,
     * nem lehet példányosítani
     */
    private RobotRecipe() {}

}
