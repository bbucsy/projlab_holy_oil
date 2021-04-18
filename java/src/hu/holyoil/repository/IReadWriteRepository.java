package hu.holyoil.repository;

/**
 * Leírja, miket kell tudnia egy írható, olvasható tárolónak. Paraméterként meg kell adni
 * a tárolt objektumok típusát.
 * */
public interface IReadWriteRepository<E> extends IReadRepository <E>{

    /**
     * Hozzáad egy <i>name</i> azonosítójú <i>element</i> objektumot.
     * @param name Azonosító.
     * @param element Az objektum.
     * */
    void Add(String name, E element);

    /**
     * Kitörli az <i>id</i> azonosítójú objektumot.
     * @return A törlés sikeressége.
     * */
    boolean Remove(String id);

}
