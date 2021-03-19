package hu.holyoil.collection;

import hu.holyoil.resource.AbstractBaseResource;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

public class BillOfMaterial {

    private static Integer ID = 0;
    private Integer myID;

    public BillOfMaterial() {
        myID = ID;
        ID++;
        resources = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "BillOfMaterial " + myID.toString();
    }

    List<AbstractBaseResource> resources;

    public void AddMaterial(AbstractBaseResource abstractBaseResource){
        System.out.println("I am BillOfMaterial " + this.toString() + "Adding material " + abstractBaseResource.toString());
    }

    public List<AbstractBaseResource> GetMaterials() {
        System.out.println("Returning my materials");
        return resources;
    }
}
