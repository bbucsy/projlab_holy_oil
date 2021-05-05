package hu.holyoil.resource;


import hu.holyoil.repository.ResourceBaseRepository;

import javax.swing.*;
import java.awt.*;

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
        this(ResourceBaseRepository.GetIdWithPrefix("Coal"));
    }

    public Coal(String name) {
        id = name;
        ResourceBaseRepository.GetInstance().Add(name, this);
    }

    /**
     * Kiírja a szenet emberileg olvasható formátumban.
     * */
    @Override
    public String toString() {
        return "Coal";
    }

    /**
     * Megvalósítja az összehasonlító metódust.
     * @param abstractBaseResource az összehasonlítandó nyersanyag
     * @return igaz, ha a paraméter Szén volt. Egyébként hamis.
     */
    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        return abstractBaseResource instanceof Coal;
    }

    /**
     * Statikus, minden robotra jellemző ikon
     */
    protected static Image image = new ImageIcon("assets/coal.gif").getImage();

    /**
     * Visszaadja az ikonját
     */
    @Override
    public Image GetImage() {
        return image;
    }
}
