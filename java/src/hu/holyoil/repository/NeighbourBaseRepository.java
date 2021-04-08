package hu.holyoil.repository;

import hu.holyoil.neighbour.INeighbour;

import java.util.HashMap;

public class NeighbourBaseRepository extends AbstractBaseRepository<INeighbour> {

    HashMap<String, INeighbour> neighbours;

    private NeighbourBaseRepository() {
        neighbours = new HashMap<>();
    }

    private static NeighbourBaseRepository neighbourBaseRepository;

    public static NeighbourBaseRepository GetInstance() {

        if (neighbourBaseRepository == null) {
            neighbourBaseRepository = new NeighbourBaseRepository();
        }

        return neighbourBaseRepository;

    }

}
