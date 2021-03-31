package hu.holyoil.skeleton;

import hu.holyoil.controller.InputOutputController;
import hu.holyoil.repository.NeighbourBaseRepository;
import hu.holyoil.repository.PlayerStorageBaseRepository;
import hu.holyoil.repository.ResourceBaseRepository;
import hu.holyoil.repository.SpaceshipBaseRepository;

/**
 * Absztrakt osztály a tesztesetek közös kezelésére
 */
public abstract class TestCase {

    /**
     * Absztrakt metódus, amelynek feladata visszaadni a teszteset nevét
     * @return teszteset neve
     */
    public abstract String Name();

    /**
     * Absztrakt metódus, amelynek feladata előre feltölteni a teszteset környezeti változóit (test fixture).
     * A kommunikációs diagram egészének és a szekvencia diagramok elejének legprogramozott megfelelője
     */
    protected abstract void load();

    /**
     * Absztrakt metódus a teszteset által vizsgált szekvencia elindítására
     */
    protected abstract void start();

    /**
     * Kifelé nem elérhető, csak a leszármazottak által elérhető konstruktor. Célja hozzáadni a jelenlegi tesztelési
     * keretrendszerhez saját példányát.
     * Maradj távol ennek módosításától!
     */
    protected TestCase() {
        TestFramework.getInstance().AddTestcase(this);
    }

    /**
     * A teszteset egészének futását kezdeményező függvény. Magában foglalja a test fixture beállítását, és a tesztelt
     * szekvencia elindítását.
     */
    public void runTestcase(){
        // load scenario
        Logger.SetEnabled(false);
        load();

        // play scenario
        System.out.println("====== " + Name() + " started ======");
        Logger.SetEnabled(true);
        start();

        // delete resource
        System.out.println("====== " + Name() + " ended ======");
        InputOutputController.GetInstance().WriteState();

        NeighbourBaseRepository.GetInstance().Clear();
        SpaceshipBaseRepository.GetInstance().Clear();
        ResourceBaseRepository.GetInstance().Clear();
        PlayerStorageBaseRepository.GetInstance().Clear();

        Logger.ClearObjects();
    }
}
