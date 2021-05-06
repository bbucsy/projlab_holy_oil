package hu.holyoil.crewmate;

import hu.holyoil.controller.AIController;
import hu.holyoil.controller.Logger;
import hu.holyoil.controller.TurnController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.SpaceshipBaseRepository;

import javax.swing.*;
import java.awt.*;

/**
 * Robotot leíró osztály.
 * Leszármazottja az AbstractCrewmate-nek (telepessel való közös tulajdonságai miatt)
 */
public class Robot extends AbstractCrewmate {
    /**
     * Statikus, minden robotra jellemző ikon
     */
    protected static Image image = new ImageIcon("assets/robot.gif").getImage();

    /**
     * Kiírja a robotot emberileg olvasható módon. Az asszociációk helyén id-ket írunk ki.
     * */
    @Override
    public String toString() {
        return "ROBOT (name:) " + id + "\n\t(asteroid name:) " + onAsteroid.GetId();
    }

    /**
     * A Robot konstruktora.
     * <p>beállítja a kezdő aszteroidát,
     *       hozzáadja az aszteroidához a robotot.
     *       A AIController-hez a gyártás során adódik hozzá. A robot mindig gyártás során példányosítódik</p>
     * @param startingAsteroid a kezdő aszteroida, amin a játékos legyártja
     */
    public Robot(Asteroid startingAsteroid) {
        this(startingAsteroid, SpaceshipBaseRepository.GetIdWithPrefix("Robot"));
    }

    //todo: comment
    public Robot(Asteroid asteroid, String name) {
        id = name;
        onAsteroid = asteroid;

        SpaceshipBaseRepository.GetInstance().Add(name, this);
        TurnController.GetInstance().RegisterEntityWithAction(this);
        AIController.GetInstance().AddRobot(this);
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

        SpaceshipBaseRepository.GetInstance().Remove(id);
    }

    /**
     * A robot alatt felrobban az aszteroida ami átlöki egy szomszédra.
     * <p>Ez a szomszéd lehet egy aktív teleporter is, amin átküldi a robotot</p>
     */
    @Override
    public void ReactToAsteroidExplosion() {
        onAsteroid.GetRandomNeighbour().ReactToMove(onAsteroid, this);
    }

    @Override
    public void Drill() {
        super.Drill();
        if (onAsteroid.GetLayerCount() == 0 && onAsteroid.GetResource() != null) {
            Logger.Log(this, "I've revealed some " + onAsteroid.GetResource().toString() + " on " + onAsteroid.GetId());
        }
    }

    /**
     * Visszaadja az ikonját
     */
    @Override
    public Image GetImage() {
        return image;
    }
}
