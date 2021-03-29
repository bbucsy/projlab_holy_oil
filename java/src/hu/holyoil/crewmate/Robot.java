package hu.holyoil.crewmate;

import hu.holyoil.Main;
import hu.holyoil.controller.AIController;
import hu.holyoil.controller.TurnController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;

/**
 * Robotot leíró osztály.
 * Leszármazottja az AbstractCrewmate-nek (telepessel való közös tulajdonságai miatt)
 */
public class Robot extends AbstractCrewmate {
    /**
     * Privát paraméter nélküli konstruktor.
     * Nem hívható kívülről
     */
    private Robot() {
        id = Main.GetId();
    }

    /**
     * Kiírja a robotot emberileg olvasható módon. Az asszociációk helyén id-ket írunk ki.
     * */
    @Override
    public String toString() {
        return "ROBOT " + id + " " + onAsteroid.GetId();
    }

    /**
     * A Robot konstruktora.
     * <p>beállítja a kezdő aszteroidát,
     *       hozzáadja az aszteroidához a robotot.
     *       A AIController-hez a gyártás során adódik hozzá. A robot mindig gyártás során példányosítódik</p>
     * @param startingAsteroid a kezdő aszteroida, amin a játékos legyártja
     */
    public Robot(Asteroid startingAsteroid) {
        id = Main.GetId();
        onAsteroid = startingAsteroid;
        Logger.RegisterObject(this, "r: Robot");
        TurnController.GetInstance().RegisterEntityWithAction(this);
        onAsteroid.AddSpaceship(this);
    }

    /**
     * Robot "meghal"
     * <p>eltávolítja a robotot a AIController singleton tárolójából és
     * eltávolítja a robotot az aszteroidáról</p>
     */
    @Override
    public void Die() {
        Logger.Log(this, "Died");
        AIController.GetInstance().RemoveRobot(this);
        onAsteroid.RemoveSpaceship(this);
        TurnController.GetInstance().RemoveEntityWithAction(this);
        Logger.Return();

    }

    /**
     * A robot alatt felrobban az aszteroida ami átlöki egy szomszédra.
     * <p>Ez a szomszéd lehet egy aktív teleporter is, amin átküldi a robotot</p>
     */
    @Override
    public void ReactToAsteroidExplosion() {
        Logger.Log(this, "ReactingToAsteroidExplosion");
        onAsteroid.GetRandomNeighbour().ReactToMove(onAsteroid, this);
        Logger.Return();
    }
}
