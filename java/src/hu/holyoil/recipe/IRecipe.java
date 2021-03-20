package hu.holyoil.recipe;

import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;

/**
 * A gyártásokat (robot és teleporter) kezelő interface
 */
public interface IRecipe {
    /**
     * legyárt egy tárgyat
     * @param iStorageCapable a gyártást végrehajtó telepes
     *                        (a tárolója tartalma miatt át kell adni)
     * @param asteroid az aszteroida amin a gyártás megtörténik
     *                 (a robot gyártásánál van jelentősége, mert a robot azonnal rááll)
     */
    void Craft(IStorageCapable iStorageCapable, Asteroid asteroid);

}
