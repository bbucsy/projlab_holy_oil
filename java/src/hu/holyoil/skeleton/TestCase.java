package hu.holyoil.skeleton;

 public abstract class TestCase {


    public abstract String Name();

    protected abstract void load();
    protected abstract void start();


    public void PlayScenario(){
        // load scenario
        Logger.setEnabled(false);
        load();

        // play scenario
        System.out.println("<" + Name() + ">" + " started\n");
        Logger.setEnabled(true);
        start();

        // delete resource
        System.out.println("<" + Name() + ">" + " Ended\n");
        Logger.ClearObjects();
    }



}
