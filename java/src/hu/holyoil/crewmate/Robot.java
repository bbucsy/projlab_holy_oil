package hu.holyoil.crewmate;

import hu.holyoil.controller.RobotController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;

/**
 * Robotot leíró osztály
 * leszármazottja az AbstractCrewmate-nek (telepessel való közös tulajdonságai miatt)
 */
public class Robot extends AbstractCrewmate {
    /**
     * PRIVÁT paraméter nélküli konstruktor
     * nem hívható kívülről
     */
    private Robot() {
    }

    /**
     * A Robot konstruktora
     * @param startingAsteroid a kezdő aszteroida, amin a játékos legyártja
     * beállítja a kezdő aszteroidát
     * hozzáadja az aszteroidához a robotot
     * A RobotController-hez a gyártás során adódik hozzá
     *                          A robot mindig gyártás során példányosítódik
     */
    public Robot(Asteroid startingAsteroid) {
        onAsteroid = startingAsteroid;
        onAsteroid.AddCrewmate(this);
    }

    /**
     * Robot "meghal"
     * eltávolítja a robotot a RobotController singleton tárolójából
     * eltávolítja a robotot az aszteroidáról
     */
    @Override
    public void Die() {
        Logger.Log(this, "Died");
        RobotController.GetInstance().RemoveRobot(this);
        onAsteroid.RemoveCrewmate(this);
        Logger.Return();

    }

    /**
     * A robot alatt felrobban az aszteroida ami átlöki egy szomszédra
     * Ez a szomszéd lehet egy aktív teleporter is, amin átküldi a robotot
     */
    @Override
    public void ReactToAsteroidExplosion() {
        Logger.Log(this, "ReactingToAsteroidExplosion");
        onAsteroid.GetRandomNeighbour().ReactToMove(onAsteroid, this);
        Logger.Return();
    }
}
