package hu.holyoil.crewmate;

import hu.holyoil.Main;
import hu.holyoil.controller.AIController;
import hu.holyoil.controller.TurnController;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;

public class Ufo extends AbstractSpaceship implements IMiner{
    /**
     * Paraméter nélküli konstruktor, nem lehet kívülről meghívni.
     */
    private Ufo(){
        id = Main.GetId();
    }

    /**
     * UFO konstruktora. A játék elején jön létre adott számú UFO.
     * @param startingAsteroid az UFO kezdő aszteroidája
     */
    public Ufo(Asteroid startingAsteroid){
        id = Main.GetId();
        onAsteroid = startingAsteroid;
        onAsteroid.AddSpaceship(this);
        TurnController.GetInstance().RegisterEntityWithAction(this);
    }

    /**
     * Kiírja z ufo-t emberileg olvasható módon. Az asszociációk helyén id-ket írunk ki.
     * */
    @Override
    public String toString() {
        return "UFO " + id + " " + onAsteroid.GetId();
    }

    /**
     * Az UFO meghal.
     */
    @Override
    public void Die() {
        Logger.Log(this, "Died");
        AIController.GetInstance().RemoveUfo(this);
        onAsteroid.RemoveSpaceship(this);
        TurnController.GetInstance().RegisterEntityWithAction(this);
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

        if (TurnController.GetInstance().HasNoActionsLeft(this)) {

            Logger.Log(this, "Cannot mine, no more moves left");
            Logger.Return();
            return;

        }

        Logger.Log(this, "Mining");
        onAsteroid.ReactToMineBy(this);
        Logger.Return();
    }
}
