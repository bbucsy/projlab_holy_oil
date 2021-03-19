package hu.holyoil.resource;

public class Iron extends AbstractBaseResource {

    private static Integer ID = 0;
    private Integer myID;

    public Iron() {
        myID = ID;
        ID++;
    }

    @Override
    public String toString() {
        return "iron " + myID.toString();
    }

    @Override
    public Boolean IsSameType(AbstractBaseResource abstractBaseResource) {
        System.out.println("I am iron " + this.toString() + " and I am being compared to " + abstractBaseResource.toString());
        return null;
    }
}
