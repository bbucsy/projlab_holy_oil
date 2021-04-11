package hu.holyoil.repository;

import hu.holyoil.neighbour.INeighbour;

import java.util.HashMap;

public class NeighbourBaseRepository extends AbstractBaseRepository<INeighbour> {

    private NeighbourBaseRepository() {}

    private static NeighbourBaseRepository neighbourBaseRepository;

    public static NeighbourBaseRepository GetInstance() {

        if (neighbourBaseRepository == null) {
            neighbourBaseRepository = new NeighbourBaseRepository();
        }

        return neighbourBaseRepository;

    }

}
