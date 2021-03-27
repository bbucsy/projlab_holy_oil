package hu.holyoil.neighbour;

import hu.holyoil.IIdentifiable;
import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.crewmate.AbstractSpaceship;

/**
 * Az aszteroida és a teleportkapu közös viselkedését leíró interface
 */
public interface INeighbour extends IIdentifiable {
    /**
     * Az űrhajók mozgására reagáló metódus
     * @param from az az aszteroida amin a Crewmate eredetileg tartózkodik
     * @param abstractSpaceship a mozgást elvégezni készülő Crewmate
     */
    void ReactToMove(Asteroid from, AbstractSpaceship abstractSpaceship);

    /**
     * Felrobban
     */
    void Explode();

    /**
     * Reagál napviharra
     */
    void ReactToSunstorm();
}
