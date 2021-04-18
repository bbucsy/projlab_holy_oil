package hu.holyoil.resource;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.ResourceBaseRepository;
import hu.holyoil.commandhandler.Logger;
/**
 * Vízjég.
 * Aszteroida magjában megtalálható nyersanyag egy fajtája.
 * Ősosztálya az AbstractBaseResource
 */
public class Water extends AbstractBaseResource {
    /**
     * Paraméter nélküli konstruktor.
     */
    public Water() {
        this(ResourceBaseRepository.GetIdWithPrefix("Water"));
    }

    public Water(String name) {
        id = name;
        ResourceBaseRepository.GetInstance().Add(name, this);
    }

    /**
     * Kiírja a vizet emberileg olvasható formátumban.
     * */
    @Override
    public String toString() {
        return "WATER (name:) " + id;
    }

    /**
     * Megvalósítja az összehasonlító metódust.
     * @param abstractBaseResource az összehasonlítandó nyersanyag
     * @return igaz, ha a paraméter Vízjég volt. Egyébként hamis.
     */
    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        return abstractBaseResource instanceof Water;
    }

    /**
     * Reagál a napközeliségre és elszublimál
     * @param asteroid az aszteroida amiben a nyersanyag megtalálható
     */
    @Override
    public void ReactToSunNearby(Asteroid asteroid) {
        Logger.Log(this,"Reacting to Sun nearby");
        asteroid.SetResource(null);
        ReactToGettingDestroyed();
        Logger.Return();
    }
}
