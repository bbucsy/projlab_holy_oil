package hu.holyoil.crewmate;

import hu.holyoil.controller.RobotController;
import hu.holyoil.neighbour.Asteroid;

public class Robot extends AbstractCrewmate {

    private static Integer ID = 0;
    private Integer myID;

    private Robot() {
        myID = ID;
        ID++;
    }

    public Robot(Asteroid startingAsteroid) {
        myID = ID;
        ID++;
        onAsteroid = startingAsteroid;
    }

    @Override
    public String toString() {
        return "Robot " + myID.toString();
    }

    @Override
    public void Die() {
        System.out.println("I am robot " + this.toString() + " and I just died");
        RobotController.getInstance().RemoveRobot(this);
        onAsteroid.RemoveCrewmate(this);
    }

    @Override
    public void ReactToAsteroidExplosion() {
        System.out.println("I am robot " + this.toString() + " and my asteroid just exploded");
        onAsteroid.GetRandomNeighbour().ReactToMove(onAsteroid, this);
    }
}
