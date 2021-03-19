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

        Logger.Log(this, "Reacting to move");
        Logger.Return();

        crewmates.add(abstractCrewmate);

        Logger.Log(this, "Removing Crewmate");
        from.RemoveCrewmate(abstractCrewmate);
        Logger.Return();

        Logger.Log(this, "Setting onAsteroid of Crewmate");
        abstractCrewmate.SetOnAsteroid(this);
        Logger.Return();

    }

    public void SetIsNearbySun(Boolean newIsNearbySun) {

        Logger.Log(this, "Setting isNearbySun");
        isNearSun = newIsNearbySun;
        Logger.Return();

    }

    public void DecNumOfLayersRemaining() {

        Logger.Log(this,"Decreasing layer by 1");

        if (numOfLayersRemaining > 0)
            numOfLayersRemaining--;

        Logger.Return();

    }

    public void ReactToMineBy(IStorageCapable iStorageCapable) {

        Logger.Log(this, "Reacting to mine");
        Logger.Return();

        if (resource != null && numOfLayersRemaining == 0) {

            Logger.Log(this, "Resource reacting to mine");
            resource.ReactToMine(this, iStorageCapable);
            Logger.Return();

        }

    }

    public void ReactToDrill() {

        Logger.Log(this, "Getting drilled");

        int layers = Logger.getInteger(this,"How many layers do I have?");

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
        Logger.Return();

        if (isNearSun && resource != null) {

            Logger.Log(this, "Resource is reacting to sun nearby");
            resource.ReactToSunNearby(this);
            Logger.Return();

        }

    }

    public void SetResource(AbstractBaseResource abstractBaseResource) {

        Logger.Log(this, "Setting resource");
        // System.out.println("I am asteroid " + this.toString() + " and my resource is being set to " + ((abstractBaseResource == null) ? "null" : abstractBaseResource.toString()));
        resource = abstractBaseResource;
        Logger.Return();

    }

    public INeighbour GetRandomNeighbour() {
        System.out.println("I am asteroid " + this.toString() + " someone is asking for my random neighbour");
        if (Main.isTestMode) {
            return TestFramework.getInstance().GetAsteroid();
        } else {
            return null;
        }
    }

    public void AddNeighbourAsteroid(Asteroid asteroid) {
        System.out.println("I am asteroid " + this.toString() + " my new neighbour is " + asteroid.toString());
        neighbouringAsteroids.add(asteroid);
    }

    public List<Asteroid> GetNeighbours() {
        System.out.println("I am asteroid " + this.toString() + " someone is asking for my neighbours");
        return neighbouringAsteroids;
    }

    public void AddCrewmate(AbstractCrewmate abstractCrewmate) {
        System.out.println("I am asteroid " + this.toString() + " my new crewmate is " + abstractCrewmate.toString());
        crewmates.add(abstractCrewmate);
    }

    public void RemoveCrewmate(AbstractCrewmate abstractCrewmate) {
        System.out.println("I am asteroid " + this.toString() + " I lost crewmate " + abstractCrewmate.toString());
        crewmates.remove(abstractCrewmate);
    }

    public void KillAllCrewmates() {
        System.out.println("I am asteroid " + this.toString() + " and I am killing all crewmates on me");
        crewmates.forEach(AbstractCrewmate::Die);
    }

    public void RemoveTeleporter() {
        System.out.println("I am asteroid " + this.toString() + " and my teleporter is getting removed");
        teleporter = null;
    }

    @Override
    public void Explode() {
        System.out.println("I am asteroid " + this.toString() + " and I am exploding");
        crewmates.forEach(AbstractCrewmate::ReactToAsteroidExplosion);
        GameController.getInstance().RemoveAsteroid(this);
        SunController.getInstance().RemoveAsteroid(this);
    }

    public TeleportGate GetTeleporter() {
        System.out.println("I am asteroid " + this.toString() + " someone is asking for my teleporter");
        return teleporter;
    }

    public void SetTeleporter(TeleportGate teleportGate) {
        System.out.println("I am asteroid " + this.toString() + " and my teleporter is being set");
        teleporter = teleportGate;
    }

}
