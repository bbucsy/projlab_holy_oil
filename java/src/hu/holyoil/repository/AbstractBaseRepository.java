package hu.holyoil.repository;

import hu.holyoil.commandhandler.Logger;

import java.util.*;

/**
 * Definiál egy tetszőleges típusú objektumot tároló tárolót.
 * Az itt tárolt dolgok hozzáadhatóak, törölhetőek, lekérhetőek id alapján és lekérhető minden tárolt objektum.
 * Kettő statikus függvénye van: név használatának ellenőrzésére és új egyedi név generálására.
 * */
public abstract class AbstractBaseRepository <E> implements IReadWriteRepository<E> {

    /**
     * Id alapján tárolja a tárolt objektumokat.
     * */
    HashMap<String, E> storedElements = new HashMap<>();

    /**
     * Hozzáad egy <i>name</i> azonosítójú objektumot a tátolt objektumokhoz, feltéve, hogy ilyen azonosítóval még
     * nincs objektum.
     * @param element A hozzáadandó objektum
     * @param name Az egyedi azonosító
     * */
    public void Add(String name, E element) {
        if (!IsNameUsed(name)) {
            storedElements.put(name, element);
            Logger.RegisterObject(element, name + ": " + element.getClass().getSimpleName());
            idsUsed.add(name);
        }
    }

    /**
     * Visszaadja az <i>id</i> azonosítójú objektumot. Ha ez nem létezik, <tt>null</tt>-lal tér vissza.
     * @param id Az azonosító
     * @return Az objektum, vagy <tt>bull</tt>.
     * */
    public E Get(String id) {
        return storedElements.get(id);
    }

    /**
     * Visszaad minden tárolt objektumot.
     * @return Minden tárolt objektum.
     * */
    public List<E> GetAll() {
        return new ArrayList<>(storedElements.values());
    }

    /**
     * Kitörli az <i>id</i> azonosítójú objektumot a tároltak közül. Felszabadítja
     * az <i>id</i> azonosítót, így az újra kiosztható.
     * @param id A törlendő azonosító.
     * @return A törlés sikeressége.
     * */
    public boolean Remove(String id) {
        boolean contained = false;
        if (storedElements.containsKey(id)) {
            storedElements.remove(id);
            contained = true;
            idsUsed.remove(id);
        }
        return contained;
    }

    /**
     * Az összes haszbált id. Az id-k globálisan egyediek, ezért ez statikus.
     * */
    public static Set<String> idsUsed = new HashSet<>();

    /**
     * Visszaadja, hogy <i>name</i> haszbált-e már valahol a rendszerben.
     * @return Használt-e valahol <i>name</i>.
     * @param name Az azonosító, amit vizsgálunk.
     * */
    public static Boolean IsNameUsed(String name) {
        return idsUsed.contains(name);
    }

    /**
     * Generál egy egyedi azonosítót, amely <i>prefix</i>-szel kezdődik.
     * @param prefix Ezzel kezdődik a generálandó azonosító.
     * @return Garantáltan egyedi azonosító.
     * */
    public static String GetIdWithPrefix(String prefix) {
        int i = 1;
        while (idsUsed.contains(prefix + i)) {
            i++;
        }
        return prefix + i;
    }

    /**
     * Kitöröl minden tárolt objektumot és nevet.
     * */
    public void Clear() {
        for (String id: storedElements.keySet()) {
            idsUsed.remove(id);
        }
        storedElements.clear();
    }

}
