package hu.holyoil.neighbour;

import hu.holyoil.Main;
import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.testframework.TestFramework;

import java.util.ArrayList;
import java.util.List;

public class Asteroid implements INeighbour {

    private static Integer ID = 0;
    private Integer myID;

    public Asteroid() {
        myID = ID;
        ID++;
        neighbouringAsteroids = new ArrayList<>();
        crewmates = new ArrayList<>();
        resource = null;
        teleporter = null;
        isNearSun = Boolean.FALSE;
        isDiscovered = Boolean.FALSE;
        numOfLayersRemaining = 0;
    }

    @Override
    public String toString() {
        return "Asteroid " + myID.toString();
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
        System.out.println("I am asteroid " + this.toString() + " and I am moved on from " + from.toString() + " by " + abstractCrewmate.toString());
        crewmates.add(abstractCrewmate);
        from.RemoveCrewmate(abstractCrewmate);
        abstractCrewmate.SetOnAsteroid(this);
    }

    public void SetIsNearbySun(Boolean newIsNearbySun) {
        System.out.println("I am asteroid " + this.toString() + " and my new isnearbysun is " + newIsNearbySun);
        isNearSun = newIsNearbySun;
    }

    public void DecNumOfLayersRemaining() {
        System.out.println("I am asteroid " + this.toString() + " and my layer number was decreased");
        numOfLayersRemaining--;
    }

    public void ReactToMineBy(IStorageCapable iStorageCapable) {
        System.out.println("I am asteroid " + this.toString() + " and I am being mined by " + iStorageCapable.toString());
        if (resource != null && numOfLayersRemaining == 0) {
            resource.ReactToMine(this, iStorageCapable);
        }
    }

    public void ReactToDrill() {
        System.out.println("I am asteroid " + this.toString() + " and I am being drilled");
        if (numOfLayersRemaining > 0) {
            DecNumOfLayersRemaining();
        }
    }

    public void ReactToSunstorm() {
        System.out.println("I am asteroid " + this.toString() + " and I am being sunstormed");
        if (numOfLayersRemaining > 0 || resource != null) {
            KillAllCrewmates();
        }
    }

    public void ReactToSunNearby() {
        System.out.println("I am asteroid " + this.toString() + " and I am reacting to being near a sun");
        if (isNearSun && resource != null) {
            resource.ReactToSunNearby(this);
        }
    }

    public void SetResource(AbstractBaseResource abstractBaseResource) {
        System.out.println("I am asteroid " + this.toString() + " and my resource is being set to " + ((abstractBaseResource == null) ? "null" : abstractBaseResource.toString()));
        resource = abstractBaseResource;
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
