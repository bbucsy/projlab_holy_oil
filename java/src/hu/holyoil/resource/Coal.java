package hu.holyoil.resource;

import hu.holyoil.collection.BillOfMaterial;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.neighbour.Asteroid;

public class Coal extends AbstractBaseResource {

    private static Integer ID = 0;
    private Integer myID;

    public Coal() {
        myID = ID;
        ID++;
    }

    @Override
    public String toString() {
        return "coal " + myID.toString();
    }

    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        System.out.println("I am coal " + this.toString() + " and I am being compared to " + abstractBaseResource.toString());
        return Boolean.TRUE;
    }
}
