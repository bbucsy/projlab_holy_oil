package hu.holyoil.collection;

import hu.holyoil.resource.AbstractBaseResource;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class BillOfMaterial {

    List<AbstractBaseResource> resources;

    public void AddMaterial(AbstractBaseResource abstractBaseResource){
        System.out.println("Adding material " + abstractBaseResource.toString());
    }

    public List<AbstractBaseResource> GetMaterials() {
        System.out.println("Returning my materials");
        return resources;
    }

}
