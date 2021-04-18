package hu.holyoil.repository;

import java.util.List;

/**
 * Leírja, miket kell tudnia egy olvasható tárolónak. Paraméterként meg kell adni
 * a tárolt objektumok típusát.
 * */
public interface IReadRepository <E> {

    /**
     * Visszaad egy <i>id</i> azonosítójú <tt>E</tt> típusú objektumot.
     * @param id Az azonosító
     * @return Az objektum.
     * */
    E Get(String id);

    /**
     * Visszaad minden tárolt objektumot.
     * @return A tátolt objektumok listája.
     * */
    List<E> GetAll();

}
