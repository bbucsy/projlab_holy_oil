package hu.holyoil.crewmate;

import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.storage.PlayerStorage;

public interface IStorageCapable {

    void CraftRobot();
    void CraftTeleportGate();
    void Mine();
    PlayerStorage getStorage();
    void PlaceTeleporter();
    void PlaceResource(AbstractBaseResource abstractBaseResource);

}
