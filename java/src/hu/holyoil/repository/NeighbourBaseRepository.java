package hu.holyoil.repository;

import hu.holyoil.neighbour.INeighbour;

import java.util.HashMap;

/**
 * <i>INeighbour</i>-öket tároló tároló. Singleton osztály.
 * */
public class NeighbourBaseRepository extends AbstractBaseRepository<INeighbour> {

    /**
     * Mivel siungleton, a konstruktora privát.
     * */
    private NeighbourBaseRepository() {}

    /**
     * Belső instance.
     * */
    private static NeighbourBaseRepository neighbourBaseRepository;

    /**
     * Visszaadja a belső instance-t.
     * */
    public static NeighbourBaseRepository GetInstance() {

        if (neighbourBaseRepository == null) {
            neighbourBaseRepository = new NeighbourBaseRepository();
        }

        return neighbourBaseRepository;

    }

}
