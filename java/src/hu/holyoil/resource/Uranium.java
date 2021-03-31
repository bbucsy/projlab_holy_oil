package hu.holyoil.resource;

import hu.holyoil.Main;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;
/**
 * Urán.
 * Aszteroida magjában megtalálható nyersanyag egy fajtája.
 * Ősosztálya az AbstractBaseResource
 */
public class Uranium extends AbstractBaseResource {

    /**
    * Az uránium akkor robban fel, ha az élete 0-ra csökken
     */
    private int health;

    /**
     * Paraméter nélküli konstruktor.
     */
    public Uranium() {
        this(InputOutputController.GetInstance().GetRandomUnusedName("Uranium "), 3);
    }

    public Uranium(String name) {
        this(name, 3);
    }

    public Uranium(String name, int health) {
        this.health = health;
        id = name;
        InputOutputController.GetInstance().RegisterObject(this, id);
        Logger.RegisterObject(this, id + ": Uranium");
    }

    /**
     * Kiírja az uránt emberileg olvasható formátumban.
     * */
    @Override
    public String toString() {
        return "URANIUM (name:)" + id + " (health:)" + health;
    }

    /**
    * Beállítja az uránium életét
    */
    public void SetHealth(int hp) {
        Logger.Log(this,"Setting health to " + hp);
        health = hp;
        Logger.Return();
    }

    /**
     * Megvalósítja az összehasonlító metódust.
     * @param abstractBaseResource az összehasonlítandó nyersanyag
     * @return igaz, ha a paraméter Urán volt. Egyébként hamis.
     */
    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        //Logger.Log(this,"Being compared to " + Logger.GetName(abstractBaseResource));
        //Logger.Return();
        return abstractBaseResource instanceof Uranium;
    }

    /**
     * Uránium reagál a napközeliségre és felrobbantja az aszteroidáját.
     * @param asteroid az aszteroida amiben a nyersanyag megtalálható
     */
    @Override
    public void ReactToSunNearby(Asteroid asteroid) {
        Logger.Log(this,"Reacting to Sun nearby");
        
        SetHealth(health-1);

        if(health <= 0) {
            asteroid.Explode();
            InputOutputController.GetInstance().RemoveObject(id);
        }
        
        Logger.Return();
    }
}
