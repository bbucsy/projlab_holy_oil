package hu.holyoil.crewmate;

import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.storage.PlayerStorage;

public class Settler extends AbstractCrewmate implements IStorageCapable {

    private PlayerStorage storage;

    @Override
    public void Die() {
        System.out.println("I am settler " + this.toString() + " and I just died");
    }

    @Override
    public void ReactToAsteroidExplosion() {
        System.out.println("I am settler " + this.toString() + " and my asteroid just exploded");
    }

    @Override
    public void CraftRobot() {
        System.out.println("I am settler " + this.toString() + " and i am crafting a robot");
    }

    @Override
    public void CraftTeleportGate() {
        System.out.println("I am settler " + this.toString() + " and i am crafting a teleportgate pair");
    }

    @Override
    public void Mine() {
        System.out.println("I am settler " + this.toString() + " and i am mining");
    }

    @Override
    public PlayerStorage getStorage() {
        System.out.println("I am settler " + this.toString() + " and i am returning my storage");
        return storage;
    }

    @Override
    public void PlaceTeleporter() {
        System.out.println("I am settler " + this.toString() + " and i am placing a teleporter");
    }

    @Override
    public void PlaceResource(AbstractBaseResource abstractBaseResource) {
        System.out.println("I am settler " + this.toString() + " and i am placing resource " + abstractBaseResource.toString());
    }
}
