package hu.holyoil.crewmate;

import hu.holyoil.controller.AIController;
import hu.holyoil.controller.GameController;
import hu.holyoil.skeleton.Logger;

public class Ufo extends AbstractSpaceship implements IMiner{
    @Override
    public void Die() {
        Logger.Log(this, "Died");
        AIController.GetInstance().RemoveUfo(this);
        onAsteroid.RemoveSpaceship(this);
        Logger.Return();
    }

    @Override
    public void ReactToAsteroidExplosion() {
        Logger.Log(this, "ReactingToAsteroidExplosion");
        Die();
        Logger.Return();
    }

    @Override
    public void Mine() {
        Logger.Log(this, "Mining");
        onAsteroid.SetResource(null);
        Logger.Return();
    }
}
