package hu.holyoil.resource;


import hu.holyoil.Main;
import hu.holyoil.controller.InputOutputController;
import hu.holyoil.skeleton.Logger;

/**
 * Szén.
 * Aszteroida magjában megtalálható nyersanyag egy fajtája.
 * Ősosztálya az AbstractBaseResource
 */
public class Coal extends AbstractBaseResource {
    /**
     * Paraméter nélküli konstruktor.
     */
    public Coal() {
        this(InputOutputController.GetInstance().GetRandomUnusedName("Coal "));
    }

    public Coal(String name) {
        id = name;
        InputOutputController.GetInstance().RegisterObject(this, id);
        Logger.RegisterObject(this, id + ": Coal");
    }

    /**
     * Kiírja a szenet emberileg olvasható formátumban.
     * */
    @Override
    public String toString() {
        return "COAL (name:)" + id;
    }

    /**
     * Megvalósítja az összehasonlító metódust.
     * @param abstractBaseResource az összehasonlítandó nyersanyag
     * @return igaz, ha a paraméter Szén volt. Egyébként hamis.
     */
    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        //Logger.Log(this,"Being compared to " + Logger.GetName(abstractBaseResource));
        //Logger.Return();
        return abstractBaseResource instanceof Coal;
    }
}
