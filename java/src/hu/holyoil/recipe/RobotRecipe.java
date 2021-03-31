package hu.holyoil.recipe;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.controller.AIController;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.crewmate.Robot;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.*;
import hu.holyoil.skeleton.Logger;
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

        Logger.Log(this, "Crafting Robot");
        Logger.Return();

        BillOfMaterial billOfMaterial = new BillOfMaterial();
        Logger.RegisterObject(billOfMaterial, "bill: BillOfMaterial");

        Iron iron = new Iron(
                InputOutputController.GetInstance().GetRandomUnusedName("iron")
        );

        Uranium uranium = new Uranium(
                InputOutputController.GetInstance().GetRandomUnusedName("uranium")
        );

        Coal coal = new Coal(
                InputOutputController.GetInstance().GetRandomUnusedName("coal")
        );

        Logger.Log(this, "Adding iron to " + Logger.GetName(billOfMaterial));
        billOfMaterial.AddMaterial(iron);
        Logger.Return();

        Logger.Log(this, "Adding uranium to " + Logger.GetName(billOfMaterial));
        billOfMaterial.AddMaterial(uranium);
        Logger.Return();

        Logger.Log(this, "Adding coal to " + Logger.GetName(billOfMaterial));
        billOfMaterial.AddMaterial(coal);
        Logger.Return();

        Logger.Log(this, "Getting player storage");
        PlayerStorage storage = iStorageCapable.GetStorage();
        Logger.Return();

        if (storage.HasEnoughOf(billOfMaterial)) {

            Logger.Log(this, "Removing bill from storage");
            storage.RemoveBill(billOfMaterial);
            Logger.Return();

            Robot robot = new Robot(asteroid);
            Logger.RegisterObject(robot, "r: Robot");

            Logger.Log(this, "Adding " + Logger.GetName(robot) + " to asteroid " + Logger.GetName(asteroid));
            asteroid.AddSpaceship(robot);
            Logger.Return();

            Logger.Log(this, "Registering " + Logger.GetName(robot) + " at the AIcontroller");
            AIController.GetInstance().AddRobot(robot);
            Logger.Return();

        }

        InputOutputController.GetInstance().RemoveObject(iron.GetId());
        InputOutputController.GetInstance().RemoveObject(uranium.GetId());
        InputOutputController.GetInstance().RemoveObject(coal.GetId());

    }

    /**
     * Singleton osztályként így lehet rá referálni
     * @return visszaad egy instance-ot
     */
    public static RobotRecipe GetInstance() {

        if (robotRecipe == null) {
            robotRecipe = new RobotRecipe();
        }

        if (Logger.GetName(robotRecipe) == null) {
            Logger.RegisterObject(robotRecipe, ": RobotRecipe");
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
