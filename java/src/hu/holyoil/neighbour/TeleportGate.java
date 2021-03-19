package hu.holyoil.neighbour;

import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.storage.PlayerStorage;

public class TeleportGate implements INeighbour {

    private static Integer ID = 0;
    private Integer myID;

    public TeleportGate() {
        myID = ID;
        ID++;
        pair = null;
        homeAsteroid = null;
        homeStorage = null;
    }

    @Override
    public String toString() {
        return "TeleportGate " + myID.toString();
    }

    private TeleportGate pair;
    private Asteroid homeAsteroid;
    private PlayerStorage homeStorage;

    @Override
    public void ReactToMove(Asteroid from, AbstractCrewmate abstractCrewmate) {
        System.out.println("I am teleportgate " + this.toString() + " and I am moved on from " + from.toString() + " by " + abstractCrewmate.toString());
    }

    public void SetPair(TeleportGate newPair) {
        System.out.println("I am teleportgate " + this.toString() + " and my pair is being set to " + newPair.toString());
        pair = newPair;
    }

    @Override
    public void Explode() {
        System.out.println("I am teleportgate " + this.toString() + " and I am exploding");
        pair.ExplodePair();
        ActuallyExplode();
    }

    private void ExplodePair() {
        System.out.println("I am teleportgate " + this.toString() + " and I am being exploded by my pair");
        ActuallyExplode();
    }

    private void ActuallyExplode() {
        if ((homeAsteroid == null && homeStorage == null) ||(homeAsteroid != null && homeStorage != null)) {
            // Error
        }
        if ((homeAsteroid == null && homeStorage != null)) {
            // in storage
            homeStorage.RemoveTeleportGate(this);
        } else {
            // this line is needed for idea to stfu
            if (homeAsteroid != null) {
                homeAsteroid.RemoveTeleporter();
            }
        }
    }

}
