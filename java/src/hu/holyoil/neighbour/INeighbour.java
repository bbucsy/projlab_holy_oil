package hu.holyoil.neighbour;

import hu.holyoil.crewmate.AbstractCrewmate;

public interface INeighbour {

    void ReactToMove(Asteroid from, AbstractCrewmate abstractCrewmate);
    void Explode();

}
