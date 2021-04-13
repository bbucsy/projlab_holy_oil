package hu.holyoil.repository;

import hu.holyoil.commandhandler.Logger;

import java.util.*;

public abstract class AbstractBaseRepository <E> implements IReadWriteRepository<E> {

    HashMap<String, E> storedElements = new HashMap<>();

    public void Add(String name, E element) {
        if (!IsNameUsed(name)) {
            storedElements.put(name, element);
            Logger.RegisterObject(element, name + ": " + element.getClass().getSimpleName());
            idsUsed.add(name);
        }
    }

    public E Get(String id) {
        return storedElements.get(id);
    }

    public List<E> GetAll() {
        return new ArrayList<>(storedElements.values());
    }

    public boolean Remove(String id) {
        boolean contained = false;
        if (storedElements.containsKey(id)) {
            storedElements.remove(id);
            contained = true;
            idsUsed.remove(id);
        }
        return contained;
    }

    public static Set<String> idsUsed = new HashSet<>();

    public static Boolean IsNameUsed(String name) {
        return idsUsed.contains(name);
    }

    public static String GetIdWithPrefix(String prefix) {
        int i = 1;
        while (idsUsed.contains(prefix + i)) {
            i++;
        }
        return prefix + i;
    }

    public void Clear() {
        for (String id: storedElements.keySet()) {
            idsUsed.remove(id);
        }
        storedElements.clear();
    }

}
