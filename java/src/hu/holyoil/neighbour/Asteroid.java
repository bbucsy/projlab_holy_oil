package hu.holyoil.neighbour;

import hu.holyoil.Main;
import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestFramework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Asteroid implements INeighbour {

    public Asteroid() {
        neighbouringAsteroids = new ArrayList<>();
        crewmates = new ArrayList<>();
        resource = null;
        teleporter = null;
        isNearSun = Boolean.FALSE;
        isDiscovered = Boolean.FALSE;
        numOfLayersRemaining = 0;
    }

    private Boolean isNearSun;
    private Boolean isDiscovered;
    private Integer numOfLayersRemaining;

    private List<Asteroid> neighbouringAsteroids;
    private TeleportGate teleporter;
    private List<AbstractCrewmate> crewmates;
    private AbstractBaseResource resource;

    @Override
    public void ReactToMove(Asteroid from, AbstractCrewmate abstractCrewmate) {

        Logger.Log(this, "Reacting to move from " + Logger.GetName(from) + " by " + Logger.GetName(abstractCrewmate));
        Logger.Return();

        Logger.Log(this, "Removing Crewmate");
        from.RemoveCrewmate(abstractCrewmate);
        Logger.Return();

        crewmates.add(abstractCrewmate);

        Logger.Log(this, "Setting onAsteroid of Crewmate");
        abstractCrewmate.SetOnAsteroid(this);
        Logger.Return();

    }

    public void SetIsNearbySun(Boolean newIsNearbySun) {

        Logger.Log(this, "Setting isNearbySun to " + newIsNearbySun.toString());
        isNearSun = newIsNearbySun;
        Logger.Return();

    }

    public void SetNumOfLayersRemaining(int newNumOfLayersRemaining) {

        Logger.Log(this, "Setting numOfLayersRemaining to " + newNumOfLayersRemaining);
        numOfLayersRemaining = newNumOfLayersRemaining;
        Logger.Return();

    }

    public void DecNumOfLayersRemaining() {

        Logger.Log(this,"Decreasing layer by 1");

        if (numOfLayersRemaining > 0)
            numOfLayersRemaining--;

        Logger.Return();

    }

    public void ReactToMineBy(IStorageCapable iStorageCapable) {

        Logger.Log(this, "Reacting to mine by " + Logger.GetName(iStorageCapable));
        Logger.Return();

        if (resource != null && numOfLayersRemaining == 0) {

            Logger.Log(this, "Resource reacting to mine");
            resource.ReactToMine(this, iStorageCapable);
            Logger.Return();

        }

    }

    public void ReactToDrill() {

        Logger.Log(this, "Getting drilled");

        int layers = Logger.GetInteger(this,"How many layers do I have?");

        if (layers >= 1) this.DecNumOfLayersRemaining();

        Logger.Return();

    }

    public void ReactToSunstorm() {

        Logger.Log(this, "Reacting to sunstorm");
        Logger.Return();

        if (numOfLayersRemaining > 0 || resource != null) {

            Logger.Log(this, "Killing all crewmates");
            KillAllCrewmates();
            Logger.Return();

        }

    }

    public void ReactToSunNearby() {

        Logger.Log(this, "Reacting to sun nearby");

        if (isNearSun && numOfLayersRemaining == 0 && resource != null) {

            Logger.Log(this, "Resource is reacting to sun nearby");
            resource.ReactToSunNearby(this);
            Logger.Return();

        }
        Logger.Return();

    }

    public void SetResource(AbstractBaseResource abstractBaseResource) {

        Logger.Log(this, "Setting resource to " + Logger.GetName(abstractBaseResource));
        resource = abstractBaseResource;
        Logger.Return();

    }

    public INeighbour GetRandomNeighbour() {

        Logger.Log(this, "Returning random neighbour");
        Logger.Return();
        if (Main.isTestMode) {
            if (neighbouringAsteroids.isEmpty())
                return teleporter;
            else
                return neighbouringAsteroids.get(0);
        } else {
            // todo
            return null;
        }

    }

    public void AddNeighbourAsteroid(Asteroid asteroid) {

        Logger.Log(this, "Adding asteroid " + asteroid + " to my neighbours");
        neighbouringAsteroids.add(asteroid);
        Logger.Return();

    }

    public List<Asteroid> GetNeighbours() {

        Logger.Log(this, "Returning my neighbours");
        Logger.Return();
        return neighbouringAsteroids;

    }

    public void AddCrewmate(AbstractCrewmate abstractCrewmate) {

        Logger.Log(this, "Adding new crewmate: " + Logger.GetName(abstractCrewmate));
        crewmates.add(abstractCrewmate);
        Logger.Return();

    }

    public void RemoveCrewmate(AbstractCrewmate abstractCrewmate) {

        Logger.Log(this, "Removing crewmate: " + Logger.GetName(abstractCrewmate));
        crewmates.remove(abstractCrewmate);
        Logger.Return();

    }

    public void KillAllCrewmates() {

        Logger.Log(this, "Killing all crewmates");
        crewmates.forEach(AbstractCrewmate::Die);
        Logger.Return();

    }

    public void RemoveTeleporter() {

        Logger.Log(this, "Removing teleporter");
        teleporter = null;
        Logger.Return();

    }

    @Override
    public void Explode() {

        Logger.Log(this, "Exploding");
        Logger.Return();

        Logger.Log(this, "Signaling to crewmates that I am exploding");
        List<AbstractCrewmate> crewmatesShallowCopy = new ArrayList<>(crewmates);
        crewmatesShallowCopy.forEach(AbstractCrewmate::ReactToAsteroidExplosion);
        Logger.Return();

        if (teleporter != null) {

            Logger.Log(this, "Exploding my teleporter");
            teleporter.Explode();
            Logger.Return();

        }

        Logger.Log(this, "Removing me from GameController");
        GameController.getInstance().RemoveAsteroid(this);
        Logger.Return();

        Logger.Log(this, "Removing me from SunController");
        SunController.getInstance().RemoveAsteroid(this);
        Logger.Return();

    }

    public TeleportGate GetTeleporter() {

        Logger.Log(this, "Returning Teleporter");
        Logger.Return();
        return teleporter;

    }

    public void SetTeleporter(TeleportGate teleportGate) {

        Logger.Log(this, "Setting teleporter to: " + Logger.GetName(teleportGate));
        teleporter = teleportGate;
        Logger.Return();

    }

}
