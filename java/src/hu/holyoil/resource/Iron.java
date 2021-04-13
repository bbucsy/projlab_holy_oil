package hu.holyoil.resource;

import hu.holyoil.repository.ResourceBaseRepository;

/**
 * Vas.
 * Aszteroida magjában megtalálható nyersanyag egy fajtája.
 * Ősosztálya az AbstractBaseResource
 */
public class Iron extends AbstractBaseResource {
    /**
     * Paraméter nélküli konstruktor.
     */
    public Iron() {
        this(ResourceBaseRepository.GetIdWithPrefix("Iron "));
    }

    public Iron(String name) {
        id = name;
        ResourceBaseRepository.GetInstance().Add(name, this);
    }

    /**
     * Kiírja a vasat emberileg olvasható formátumban.
     * */
    @Override
    public String toString() {
        return "IRON (name:) " + id;
    }

    /**
     * Megvalósítja az összehasonlító metódust.
     * @param abstractBaseResource az összehasonlítandó nyersanyag
     * @return igaz, ha a paraméter Vas volt. Egyébként hamis.
     */
    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        //Logger.Log(this,"Being compared to " + Logger.GetName(abstractBaseResource));
        //Logger.Return();
        return abstractBaseResource instanceof Iron;
    }
}
