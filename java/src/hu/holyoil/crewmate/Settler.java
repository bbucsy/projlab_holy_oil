package hu.holyoil.crewmate;

import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.recipe.RobotRecipe;
import hu.holyoil.recipe.TeleporterRecipe;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.storage.PlayerStorage;

public class Settler extends AbstractCrewmate implements IStorageCapable {

    private static Integer ID = 0;
    private Integer myID;

    private Settler() {
        myID = ID;
        ID++;
        storage = new PlayerStorage();
    }

    public Settler(Asteroid startingAsteroid) {
        myID = ID;
        ID++;
        storage = new PlayerStorage();
        onAsteroid = startingAsteroid;
    }

    @Override
    public String toString() {
        return "Settler " + myID.toString();
    }

    private PlayerStorage storage;

    @Override
    public void Die() {
        System.out.println("I am settler " + this.toString() + " and I just died");
        GameController.getInstance().RemoveSettler(this);
        // Handle teleportgate logic
        onAsteroid.RemoveCrewmate(this);
    }

    @Override
    public void ReactToAsteroidExplosion() {
        System.out.println("I am settler " + this.toString() + " and my asteroid just exploded");
        Die();
    }

    @Override
    public void CraftRobot() {
        System.out.println("I am settler " + this.toString() + " and i am crafting a robot");
        RobotRecipe.getInstance().Craft(this, onAsteroid);
    }

    @Override
    public void CraftTeleportGate() {
        System.out.println("I am settler " + this.toString() + " and i am crafting a teleportgate pair");
        TeleporterRecipe.getInstance().Craft(this, onAsteroid);
    }

    @Override
    public void Mine() {
        System.out.println("I am settler " + this.toString() + " and i am mining");
        onAsteroid.ReactToMineBy(this);
    }

    @Override
    public PlayerStorage GetStorage() {
        System.out.println("I am settler " + this.toString() + " and i am returning my storage");
        return storage;
    }

    @Override
    public void PlaceTeleporter() {
        System.out.println("I am settler " + this.toString() + " and i am placing a teleporter");
        // Handle teleportgate logic
    }

    @Override
    public void PlaceResource(AbstractBaseResource abstractBaseResource) {
        System.out.println("I am settler " + this.toString() + " and i am placing resource " + abstractBaseResource.toString());
        onAsteroid.SetResource(abstractBaseResource);
    }
}
