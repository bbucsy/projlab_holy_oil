package hu.holyoil.crewmate;

import hu.holyoil.controller.RobotController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;

public class Robot extends AbstractCrewmate {

    private Robot() {
    }

    public Robot(Asteroid startingAsteroid) {
        onAsteroid = startingAsteroid;
    }

    @Override
    public void Die() {
        Logger.Log(this, "Died");
        RobotController.getInstance().RemoveRobot(this);
        onAsteroid.RemoveCrewmate(this);
        Logger.Return();

    }

    @Override
    public void ReactToAsteroidExplosion() {
        Logger.Log(this, "ReactingToAsteroidExplosion");
        onAsteroid.GetRandomNeighbour().ReactToMove(onAsteroid, this);
        Logger.Return();

    }
}
