package hu.holyoil.neighbour;

import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.storage.PlayerStorage;

public class TeleportGate implements INeighbour {

    public TeleportGate() {
        pair = null;
        homeAsteroid = null;
        homeStorage = null;
    }

    private TeleportGate pair;
    private Asteroid homeAsteroid;

    public TeleportGate GetPair() {
        return pair;
    }

    public Asteroid GetHomeAsteroid() {
        return homeAsteroid;
    }

    public void SetHomeAsteroid(Asteroid homeAsteroid) {
        this.homeAsteroid = homeAsteroid;
    }

    public PlayerStorage GetHomeStorage() {
        return homeStorage;
    }

    public void SetHomeStorage(PlayerStorage homeStorage) {
        this.homeStorage = homeStorage;
    }

    private PlayerStorage homeStorage;

    @Override
    public void ReactToMove(Asteroid from, AbstractCrewmate abstractCrewmate) {

        Logger.Log(this, "Reacting to move from " + Logger.GetName(from) + " by " + Logger.GetName(abstractCrewmate));
        if (pair.GetHomeAsteroid() != null) {
            pair.GetHomeAsteroid().ReactToMove(from, abstractCrewmate);
        }
        Logger.Return();

    }

    public void SetPair(TeleportGate newPair) {

        Logger.Log(this, "Setting my pair to " + Logger.GetName(newPair));
        pair = newPair;
        Logger.Return();

    }

    @Override
    public void Explode() {

        Logger.Log(this, "Exploding");
        pair.ExplodePair();
        ActuallyExplode();
        Logger.Return();

    }

    private void ExplodePair() {

        Logger.Log(this, "Being exploded by pair");
        ActuallyExplode();
        Logger.Return();

    }

    private void ActuallyExplode() {
        if ((homeAsteroid == null && homeStorage == null) ||(homeAsteroid != null && homeStorage != null)) {
            // Error
            //Logger.Log(this, "Caused an error");
            //Logger.Return();
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
