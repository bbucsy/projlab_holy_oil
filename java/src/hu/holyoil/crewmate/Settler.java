package hu.holyoil.crewmate;

import hu.holyoil.controller.GameController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.recipe.RobotRecipe;
import hu.holyoil.recipe.TeleporterRecipe;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.storage.PlayerStorage;

public class Settler extends AbstractCrewmate implements IStorageCapable {

    private Settler() {
        storage = new PlayerStorage();
    }

    public Settler(Asteroid startingAsteroid) {
        storage = new PlayerStorage();
        onAsteroid = startingAsteroid;
        onAsteroid.AddCrewmate(this);
    }


    private PlayerStorage storage;

    @Override
    public void Die() {
        Logger.Log(this, "Died");

        GameController.GetInstance().RemoveSettler(this);

        if (storage.GetOneTeleporter() != null) {
            storage.GetOneTeleporter().Explode();
        }

        onAsteroid.RemoveCrewmate(this);
        Logger.Return();
    }

    @Override
    public void ReactToAsteroidExplosion() {
        Logger.Log(this, "Reacting to asteroid explosion");
        Die();
        Logger.Return();
    }

    @Override
    public void CraftRobot() {
        Logger.Log(this, "Crafting robot");
        RobotRecipe.GetInstance().Craft(this, onAsteroid);
        Logger.Return();
    }

    @Override
    public void CraftTeleportGate() {
        Logger.Log(this, "Crafting teleport gate pair.");
        TeleporterRecipe.GetInstance().Craft(this, onAsteroid);
        Logger.Return();
    }

    @Override
    public void Mine() {
        Logger.Log(this, "Mining");
        onAsteroid.ReactToMineBy(this);
        Logger.Return();
    }

    @Override
    public PlayerStorage GetStorage() {
        Logger.Log(this,"Returning " + Logger.GetName(storage));
        Logger.Return();
        return storage;
    }

    @Override
    public void PlaceTeleporter() {
        Logger.Log(this, "Place teleporter");

        TeleportGate storageTeleporter = storage.GetOneTeleporter();
        TeleportGate asteroidTeleporter = onAsteroid.GetTeleporter();

        if (storageTeleporter != null && asteroidTeleporter == null) {

            storageTeleporter.SetHomeAsteroid(onAsteroid);
            onAsteroid.SetTeleporter(storageTeleporter);

            storage.RemoveTeleportGate(storageTeleporter);

        }

        Logger.Return();
    }

    @Override
    public void PlaceResource(AbstractBaseResource abstractBaseResource) {

        Logger.Log(this, "Placing resource " + Logger.GetName(abstractBaseResource));
        if(onAsteroid.GetResource()==null)
             onAsteroid.PutResource(this, abstractBaseResource);

        Logger.Return();
    }
}
