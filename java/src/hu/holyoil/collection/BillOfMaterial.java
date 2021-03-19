package hu.holyoil.collection;

import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.skeleton.Logger;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

public class BillOfMaterial {

    List<AbstractBaseResource> resources;

    public BillOfMaterial() {
        resources = new ArrayList<>();
    }

    public void AddMaterial(AbstractBaseResource abstractBaseResource){
        Logger.Log(this,"Adding Material: " + Logger.getName(abstractBaseResource));
        Logger.Return();
    }

    public List<AbstractBaseResource> GetMaterials() {
        Logger.Log(this,"Returning material");
        Logger.Return();
        return resources;
    }
}
