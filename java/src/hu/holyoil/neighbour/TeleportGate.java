package hu.holyoil.neighbour;

import hu.holyoil.crewmate.AbstractCrewmate;

public class TeleportGate implements INeighbour {
    @Override
    public void ReactToMove(Asteroid from, AbstractCrewmate abstractCrewmate) {
        System.out.println("I am teleportgate " + this.toString() + " and I am moved on from " + from.toString() + " by " + abstractCrewmate.toString());
    }

    @Override
    public void Explode() {
        System.out.println("I am teleportgate " + this.toString() + " and I am exploding");
    }

    public void ExplodePair() {
        System.out.println("I am teleportgate " + this.toString() + " and I am being exploded by my pair");
    }

}
