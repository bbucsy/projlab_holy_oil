package hu.holyoil.resource;

import hu.holyoil.skeleton.Logger;

public class Iron extends AbstractBaseResource {

    public Iron() {

    }


    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        Logger.Log(this,"Being compared to " + Logger.GetName(abstractBaseResource));
        Logger.Return();
        return abstractBaseResource instanceof Iron;
    }
}
