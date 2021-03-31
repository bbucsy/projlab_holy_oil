package hu.holyoil.crewmate;

import hu.holyoil.controller.AIController;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.SpaceshipBaseRepository;
import hu.holyoil.skeleton.Logger;

public class Ufo extends AbstractSpaceship implements IMiner{

    /**
     * UFO konstruktora. A játék elején jön létre adott számú UFO.
     * @param startingAsteroid az UFO kezdő aszteroidája
     */
    public Ufo(Asteroid startingAsteroid){
        this(startingAsteroid, SpaceshipBaseRepository.GetIdWithPrefix("Ufo "));
    }

    public Ufo(Asteroid asteroid, String name) {

        id = name;
        onAsteroid = asteroid;
        onAsteroid.AddSpaceship(this);
        SpaceshipBaseRepository.GetInstance().Add(name, this);

    }

    /**
     * Kiírja z ufo-t emberileg olvasható módon. Az asszociációk helyén id-ket írunk ki.
     * */
    @Override
    public String toString() {
        return "UFO (name:)" + id + " (asteroid name:)" + onAsteroid.GetId();
    }

    /**
     * Az UFO meghal.
     */
    @Override
    public void Die() {
        Logger.Log(this, "Died");
        AIController.GetInstance().RemoveUfo(this);
        onAsteroid.RemoveSpaceship(this);
        SpaceshipBaseRepository.GetInstance().Remove(id);
        Logger.Return();
    }

    /**
     * Az UFO alatt felrobbanó aszteroidára reagál és meghal.
     */
    @Override
    public void ReactToAsteroidExplosion() {
        Logger.Log(this, "ReactingToAsteroidExplosion");
        Die();
        Logger.Return();
    }

    /**
     * Az UFO kibányássza egy aszteroida nyersanyagát, ami a játékosok számára többé nem hozzáférhető.
     */
    @Override
    public void Mine() {
        Logger.Log(this, "Mining");
        onAsteroid.ReactToMineBy(this);
        Logger.Return();
    }
}
