package hu.holyoil.skeleton;

public abstract class TestCase {


    public abstract String Name();

    protected abstract void load();
    protected abstract void start();

    // Do not touch.
    protected TestCase() {
        // System.out.println(this.Name());
        TestFramework.getInstance().AddTestcase(this);
    }

    public void runTestcase(){
        // load scenario
        Logger.SetEnabled(false);
        load();

        // play scenario
        System.out.println("<" + Name() + ">" + " started\n");
        Logger.SetEnabled(true);
        start();

        // delete resource
        System.out.println("<" + Name() + ">" + " Ended\n");
        Logger.ClearObjects();
    }



}
