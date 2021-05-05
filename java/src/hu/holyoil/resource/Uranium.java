package hu.holyoil.resource;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.ResourceBaseRepository;

import java.util.Collections;

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
        this(ResourceBaseRepository.GetIdWithPrefix("Uranium"), 3);
    }

    public Uranium(String name) {
        this(name, 3);
    }

    public Uranium(String name, int health) {
        this.health = health;
        id = name;
        ResourceBaseRepository.GetInstance().Add(name, this);
    }

    /**
     * Kiírja az uránt emberileg olvasható formátumban.
     * */
    @Override
    public String toString() {
        return "Uranium " + String.join("", Collections.nCopies(health, "♥"));
    }

    /**
    * Beállítja az uránium életét
    */
    public void SetHealth(int hp) {
        health = hp;
    }

    /**
     * Megvalósítja az összehasonlító metódust.
     * @param abstractBaseResource az összehasonlítandó nyersanyag
     * @return igaz, ha a paraméter Urán volt. Egyébként hamis.
     */
    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        return abstractBaseResource instanceof Uranium;
    }

    /**
     * Uránium reagál a napközeliségre és felrobbantja az aszteroidáját.
     * @param asteroid az aszteroida amiben a nyersanyag megtalálható
     */
    @Override
    public void ReactToSunNearby(Asteroid asteroid) {
        SetHealth(health-1);

        if(health <= 0) {
            asteroid.Explode();
        }
    }
}
