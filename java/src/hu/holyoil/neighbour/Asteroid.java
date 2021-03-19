package hu.holyoil.neighbour;

import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.skeleton.Logger;

import java.util.List;

public class Asteroid implements INeighbour {

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
    }

    public void SetIsNearbySun(Boolean newIsNearbySun) {
        System.out.println("I am asteroid " + this.toString() + " and my new isnearbysun is " + newIsNearbySun);
    }

    public void DecNumOfLayersRemaining() {
        Logger.Log(this,"Decreesing layer by 1");
        Logger.Return();
    }

    public void ReactToMineBy(IStorageCapable iStorageCapable) {
        System.out.println("I am asteroid " + this.toString() + " and I am being mined by " + iStorageCapable.toString());
    }

    public void ReactToDrill() {
        Logger.Log(this, "Getting drilled");

        int layers = Logger.getInteger(this,"How many layers do I have?");

        if (layers >= 1) this.DecNumOfLayersRemaining();

        Logger.Return();
    }

    public void ReactToSunstorm() {
        System.out.println("I am asteroid " + this.toString() + " and I am being sunstormed");
    }

    public void ReactToSunNearby() {
        System.out.println("I am asteroid " + this.toString() + " and I am reacting to being near a sun");
    }

    public void SetResource(AbstractBaseResource abstractBaseResource) {
        System.out.println("I am asteroid " + this.toString() + " and my resource is being set to " + abstractBaseResource.toString());
    }

    public INeighbour GetRandomNeighbour() {
        System.out.println("I am asteroid " + this.toString() + " someone is asking for my random neighbour");
        return null;
    }

    public void AddNeighbourAsteroid(Asteroid asteroid) {
        System.out.println("I am asteroid " + this.toString() + " my new neighbour is " + asteroid.toString());
    }

    public List<Asteroid> GetNeighbours() {
        System.out.println("I am asteroid " + this.toString() + " someone is asking for my neighbours");
        return neighbouringAsteroids;
    }

    public void AddCrewmate(AbstractCrewmate abstractCrewmate) {
        System.out.println("I am asteroid " + this.toString() + " my new crewmate is " + abstractCrewmate.toString());
    }

    public void RemoveCrewmate(AbstractCrewmate abstractCrewmate) {
        System.out.println("I am asteroid " + this.toString() + " I lost crewmate " + abstractCrewmate.toString());
    }

    public void KillAllCrewmates() {
        System.out.println("I am asteroid " + this.toString() + " and I am killing all crewmates on me");
    }

    public void RemoveTeleporter() {
        System.out.println("I am asteroid " + this.toString() + " and my teleporter is getting removed");
    }

    @Override
    public void Explode() {
        System.out.println("I am asteroid " + this.toString() + " and I am exploding");
    }

    public TeleportGate GetTeleporter() {
        System.out.println("I am asteroid " + this.toString() + " someone is asking for my teleporter");
        return teleporter;
    }

    public void SetTeleporter(TeleportGate teleportGate) {
        System.out.println("I am asteroid " + this.toString() + " and my teleporter is being set");
    }

}
