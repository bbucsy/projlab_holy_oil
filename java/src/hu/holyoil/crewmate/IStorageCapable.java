package hu.holyoil.crewmate;

import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.storage.PlayerStorage;

/**
 * Interface amely lehetővé teszi a telepeseknek hogy tudjanak:
 *                 <p> tárolni,
 *                  gyártani,
 *                  nyersanyagot felvenni és lerakni.</p>
 */
public interface IStorageCapable extends IStepping {
    /**
     * Robot gyártása
     */
    void CraftRobot();

    /**
     * Teleporter gyártása
     */
    void CraftTeleportGate();


    /**
     * Visszaadja a telepes inventory-ját a jelenlegi állapotban
     * @return a telepes inventory-ja
     */
    PlayerStorage GetStorage();

    /**
     * A telepes lerak egy teleportert az aktuális aszteroidáján
     */
    void PlaceTeleporter();

    /**
     * A telepes egy nyersanyaggal feltölti az aszteroidát ami a tárolójában van
     * @param abstractBaseResource egy telepesnél található nyersanyag
     */
    void PlaceResource(AbstractBaseResource abstractBaseResource);

}
