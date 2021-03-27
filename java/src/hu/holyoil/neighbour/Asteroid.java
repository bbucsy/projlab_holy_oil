package hu.holyoil.neighbour;

import hu.holyoil.Main;
import hu.holyoil.controller.GameController;
import hu.holyoil.controller.SunController;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.IMiner;
import hu.holyoil.crewmate.IStorageCapable;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.skeleton.Logger;
import hu.holyoil.skeleton.TestFramework;
import hu.holyoil.storage.PlayerStorage;

import java.util.*;

/**
 * Aszteroidákat leíró osztály.
 * Implementálja az INeighbour interfacet.
 */
public class Asteroid implements INeighbour {
    /**
     * Konstruktor
     * Inicializálja a tagváltozókat, a listákat üresen. Az aszteroida létrehozáskor üres, nincs napközelben, és nincs felfedezve a játékosok által.
     * A kérgének vastagsága 0.
     * Nincs rajta teleporter.
     */
    public Asteroid() {
        neighbouringAsteroids = new ArrayList<>();
        spaceships = new ArrayList<>();
        resource = null;
        teleporter = null;
        isNearSun = Boolean.FALSE;
        isDiscovered = Boolean.FALSE;
        numOfLayersRemaining = 0;
    }

    /**
     * Akkor igaz, amikor az aszteroida napközelben van
     */
    private Boolean isNearSun;
    /**
     * Akkor igaz, ha már egyszer rálépett egy telepes vagy egy robot. Alapból hamis.
     */
    private Boolean isDiscovered;
    /**
     * A hátralévő fúrnivaló kéreg vastagságát mutatja
     */
    private Integer numOfLayersRemaining;
    /**
     * A szomszédos aszteroidák listája
     * <p>Egy aszteroida akkor szomszéd, ha a legénység számára elérhető egy lépéssel. Kölcsönös kapcsolat.</p>
     */
    private List<Asteroid> neighbouringAsteroids;
    /**
     * Az aszteroidához tartozó teleporter.
     * <p>Lehet null, ha nincs rajta teleporter. Egyszerre egy aszteroidán egy teleporter lehet.</p>
     */
    private TeleportGate teleporter;
    /**
     * Az aszteroidán tartózkodó telepesek és robotok listája
     */
    private List<AbstractSpaceship> spaceships;
    /**
     * Az aszteroida magja.
     * <p>Lehet null, akkor az aszteroida üres.</p>
     */
    private AbstractBaseResource resource;

    /**
     * A legénység mozgását kezelő függvény.
     * <p>
     *     A Spaceshipt leveszi a kapott forrás aszteroidáról.
     *     A Spaceshipt hozzáadja a saját tárolójához.
     *     A Spaceship aszteroidáját átállítja magára.
     * </p>
     * @param from az az aszteroida amin a Spaceship eredetileg tartózkodik
     * @param abstractSpaceship a mozgást elvégezni készülő Spaceship
     */
    @Override
    public void ReactToMove(Asteroid from, AbstractSpaceship abstractSpaceship) {
        Logger.Log(this, "Reacting to move  by " + Logger.GetName(abstractSpaceship));

        from.RemoveSpaceship(abstractSpaceship);
        AddSpaceship(abstractSpaceship);
        abstractSpaceship.SetOnAsteroid(this);

        Logger.Return();
    }

    /**
     * Mozgatja a kapott teleportert egy szomszédos aszteroidára.
     * A szomszédok listáján elindul egy random indexen, és sorban vizsgálja van-e teleportere. Ekkor a saját teleporterét null-ra állítja.
     * Ha körbeért és mindegyiknek van teleportere, nem tud mozogni.
     */
    public void ReactToTeleporterMoving(){
        Logger.Log(this, "Reacting to moving teleporter: " + Logger.GetName(teleporter));

        int chosenIndex= new Random().nextInt(neighbouringAsteroids.size());
        int start = chosenIndex;
        boolean canMove = true;
        while(canMove && neighbouringAsteroids.get(chosenIndex).GetTeleporter()!=null){
            if(chosenIndex==neighbouringAsteroids.size()-1){
                chosenIndex=-1;
            }
            chosenIndex++;
            if(chosenIndex==start){
                canMove = false;
            }
        }
        if(canMove){
            neighbouringAsteroids.get(chosenIndex).SetTeleporter(teleporter);
            teleporter.SetHomeAsteroid(neighbouringAsteroids.get(chosenIndex));
            teleporter=null;
        }
        else {
            Logger.Log(this, "All neighbours already have a teleporter, cannot move");
            Logger.Return();
        }
        Logger.Return();
    }

    /**
     * A telepes megpróbál lerakni egy nyersanyagot az aszteroida magjába
     * <p>
     *     Ez csak akkor végrehajtható ha a kéreg teljesen ki lett fúrva ÉS az aszteroida üres.
     * </p>
     * @param s a telepes, akinél a lerakni kívánt nyersanyag van
     * @param res a lerakni kívánt nyersanyag
     */
    public void PutResource(Settler s, AbstractBaseResource res) {
        Logger.Log(this, "Putting down resource from settler to asteroid core.");
        if (numOfLayersRemaining == 0 && resource == null) {
            res.ReactToPlace(this, s);
        }
        Logger.Return();
    }

    /**
     * Beállítja a napközelséget jelző logikai változót
     * <p>
     *     Ezt a SunController képes megtenni egy lépéssel.
     * </p>
     * @param newIsNearbySun új logikai értéke a napközelségnek, lehet igaz és hamis is
     */
    public void SetIsNearbySun(Boolean newIsNearbySun) {

        Logger.Log(this, "Setting isNearbySun to " + newIsNearbySun.toString());
        isNearSun = newIsNearbySun;
        Logger.Return();

    }

    /**
     * Beállítja a még magig fúrandó kéregvastagságot.
     * @param newNumOfLayersRemaining új értéke a kéreg vastagsnak
     */
    public void SetNumOfLayersRemaining(int newNumOfLayersRemaining) {

        Logger.Log(this, "Setting numOfLayersRemaining to " + newNumOfLayersRemaining);
        numOfLayersRemaining = newNumOfLayersRemaining;
        Logger.Return();

    }

    /**
     * Eggyel csökkenti a magig fúrandó réteg vastagságát.
     * <p>Erre egy Spaceship képes egy fúrás lépéssel.</p>
     */
    public void DecNumOfLayersRemaining() {

        Logger.Log(this,"Decreasing layer by 1");

        if (numOfLayersRemaining > 0)
            numOfLayersRemaining--;

        Logger.Return();

    }

    /**
     * Lekezeli amikor egy ufo megpróbálja kibányászni a magját.
     * <p>
     *     Ha az aszteroida magja nem üres ÉS már teljesen ki van fúrva a kérge (tehát a vastagsága 0), meghívja a nyersanyag reagáló függvényét.
     *     Az aszteroida magját innentől a Resource kezeli.
     * </p>
     * @param iMiner a bányászni készülő ufo
     */
    public void ReactToMineBy(IMiner iMiner) {

        Logger.Log(this, "Reacting to mine by " + Logger.GetName(iMiner));
        Logger.Return();

        if (resource != null && numOfLayersRemaining == 0) {

            Logger.Log(this, "Resource reacting to mine");
            resource.ReactToMine(this, iMiner);
            Logger.Return();

        }

    }

    /**
     * Lekezeli amikor egy telepes megpróbálja kibányászni a magját.
     * <p>
     *     Ha az aszteroida magja nem üres ÉS már teljesen ki van fúrva a kérge (tehát a vastagsága 0), meghívja a nyersanyag reagáló függvényét.
     *     A telepes tárolóját és az aszteroida magját innentől a Resource kezeli.
     * </p>
     * @param iMiner a bányászni készülő telepes
     * @param storage a telepes tárolója
     */
    public void ReactToMineBy(IMiner iMiner, PlayerStorage storage) {

        Logger.Log(this, "Reacting to mine by " + Logger.GetName(iMiner));
        Logger.Return();

        if (resource != null && numOfLayersRemaining == 0) {

            Logger.Log(this, "Resource reacting to mine");
            resource.ReactToMine(this, iMiner, storage);
            Logger.Return();

        }

    }

    /**
     * Az aszteroida reagál ha egy Spaceship megpróbál rajta fúrni.
     * <p>
     *     Ha még van hátra fúrnivaló kéreg eggyel csökkenti a kéreg vastagságát, egyébként nem történik semmi.
     * </p>
     */
    public void ReactToDrill() {

        Logger.Log(this, "Getting drilled");

        if (numOfLayersRemaining>= 1) this.DecNumOfLayersRemaining();

        Logger.Return();

    }

    /**
     * Az szteroida reagál a napviharra.
     * <p>
     *     Ha az aszteroida magja NEM üres VAGY az aszteroida kérge még nincs teljesen kifúrva:
     *     az összes Spaceship meghal a spaceships listán.
     * </p>
     */
    public void ReactToSunstorm() {

        Logger.Log(this, "Reacting to sunstorm");
        Logger.Return();

        if (numOfLayersRemaining > 0 || resource != null) {

            Logger.Log(this, "Killing all spaceships");
            KillAllSpaceships();
            Logger.Return();

        }

        if (teleporter != null) {
            teleporter.ReactToSunstorm();
        }

    }

    /**
     * Az aszteroida reagál a napközeliségre.
     * <p>
     *     Minden körben meghívódik. Reakció akkor történik, ha az isNearSun változó igaz ÉS a kéreg ki van fúrva ÉS az aszteroida nem üres.
     *     Ebben az esetben meghívódik a megban lévő nyersanyag napközeliségre reagáló függvénye: Resource.ReactToSunNearby(Asteroid).
     * </p>
     */
    public void ReactToSunNearby() {

        Logger.Log(this, "Reacting to sun nearby");

        if (isNearSun && numOfLayersRemaining == 0 && resource != null) {

            Logger.Log(this, "Resource is reacting to sun nearby");
            resource.ReactToSunNearby(this);
            Logger.Return();

        }
        Logger.Return();

    }

    /**
     * Visszaadja a magban található nyersanyagot.
     * @return visszaadja a resource tagváltozót
     */
    public AbstractBaseResource GetResource(){
        Logger.Log(this, "Returning resource");
        Logger.Return();
        return resource;
    }

    /**
     * Beállítja a magban található nyersanyagot.
     * @param abstractBaseResource erre a nyersanyagra állítódik a mag
     */
    public void SetResource(AbstractBaseResource abstractBaseResource) {
        Logger.Log(this, "Setting resource to " + Logger.GetName(abstractBaseResource));
        resource = abstractBaseResource;
        Logger.Return();
    }

    /**
     * Visszaad egy véletlen szomszédot.
     * <p>
     *     A robot mozgásánál és robbanástól való lökésénél használatos.
     * </p>
     * <p>
     *     Jelenleg a tesztelésre van írva.
     *     A tesztelés alatt akkor kapunk teleportert, ha nincs szomszédos aszteroida, és akkor aszteroidát ha nincs lerakva teleporter.
     * </p>
     * <p>
     *     Nem tesztelésnél: Egy logikai változó jelzi hogy adhat-e vissza teleportert. Ha az aszteroidán VAN teleporter és annak a teleporternek VAN párja, adhat.
     *     Egyébként egy akkora intervallumból választunk ahány szomszédja van az aszteroidának. Ha lehet teleporterünk, ez egyel nagyobb.
     * </p>
     * <p>
     *     Végül ha a kapott véletlen indexünk nem fér bele a szomszédok listájának indexébe egy teleportert ad vissza, egyébként az adott indexű aszteroidát.
     * </p>
     * <p>
     *     Ez többek között azért kell, hogy amikor a robot alatt felrobban az aszteroida és átmenne egy teleporteren, csak akkor tehesse azt meg, ha annak a teleporternek van aktív párja.
     * </p>
     * @return
     */
    public INeighbour GetRandomNeighbour() {
        Logger.Log(this, "Returning random neighbour");
        Logger.Return();
        if (Main.isTestMode) {
            if (neighbouringAsteroids.isEmpty())
                return teleporter;
            else
                return neighbouringAsteroids.get(0);
        } else {

            Random random = new Random();
            boolean canChooseTeleporter = false;
            if (teleporter != null) {
                if (teleporter.GetPair().GetHomeAsteroid() != null) {
                    canChooseTeleporter = true;
                }
            }

            int chosenIndex = random.nextInt() %
                    (neighbouringAsteroids.size() + (canChooseTeleporter ? 0 : 1));

            return chosenIndex == neighbouringAsteroids.size() ?
                    teleporter :
                    neighbouringAsteroids.get(chosenIndex);
        }

    }

    /**
     * Hozzáad egy új szomszédot az aszteroidának
     * @param asteroid az új szomszéd
     */
    public void AddNeighbourAsteroid(Asteroid asteroid) {

        Logger.Log(this, "Adding asteroid " + asteroid + " to my neighbours");
        neighbouringAsteroids.add(asteroid);
        Logger.Return();

    }

    /**
     * Visszaadja a szomszédok listáját.
     * @return a neighbouringAsteroids lista
     * @return a neighbouringAsteroids lista
     */
    public List<Asteroid> GetNeighbours() {

        Logger.Log(this, "Returning my neighbours");
        Logger.Return();
        return neighbouringAsteroids;

    }

    /**
     * Felvesz egy új Spaceshipt az aszteroidára
     * @param abstractSpaceship az aszteroidára lépő telepes vagy robot
     */
    public void AddSpaceship(AbstractSpaceship abstractSpaceship) {

        Logger.Log(this, "Adding new Spaceship: " + Logger.GetName(abstractSpaceship));
        spaceships.add(abstractSpaceship);
        Logger.Return();

    }

    /**
     * Elvesz egy Spaceshipt az aszteroidáról
     * @param abstractSpaceship a törlendő telepes vagy robot
     */
    public void RemoveSpaceship(AbstractSpaceship abstractSpaceship) {

        Logger.Log(this, "Removing Spaceship: " + Logger.GetName(abstractSpaceship));
        spaceships.remove(abstractSpaceship);
        Logger.Return();

    }

    /**
     * Megöli az aszteroidán tartózkodó összes egységet.
     */
    public void KillAllSpaceships() {

        Logger.Log(this, "Killing all spaceships");
        spaceships.forEach(AbstractSpaceship::Die);
        Logger.Return();
    }

    /**
     * Törli az aszteroidán található teleportert.
     */
    public void RemoveTeleporter() {

        Logger.Log(this, "Removing teleporter");
        teleporter = null;
        Logger.Return();

    }

    /**
     * Az aszteroida felrobban.
     * <p>
     *     Meghívja minden Spaceship robbanásra reagáló metódusát. Ha az aszteroidán volt teleporter azt felrobbantja.
     *     Eltávolítja magát a GameControllerből, majd a SunControllerből.
     * </p>
     */
    @Override
    public void Explode() {
        Logger.Log(this, "Exploding");

        Logger.Log(this, "Signaling to spaceships that I am exploding");
        List<AbstractSpaceship> spaceshipsShallowCopy = new ArrayList<>(spaceships);
        spaceshipsShallowCopy.forEach(AbstractSpaceship::ReactToAsteroidExplosion);
        Logger.Return();

        if (teleporter != null) {

            Logger.Log(this, "Exploding my teleporter");
            teleporter.Explode();
            Logger.Return();

        }

        Logger.Log(this, "Removing me from GameController");
        GameController.GetInstance().RemoveAsteroid(this);
        Logger.Return();

        Logger.Log(this, "Removing me from SunController");
        SunController.GetInstance().RemoveAsteroid(this);
        Logger.Return();

        Logger.Return();
    }

    /**
     * Visszaadja az aszteroidán található teleportert.
     * <p>
     *     Ez lehet null, ha az aszteroidán nincs teleporter.
     * </p>
     * @return null, vagy az aszteroidán található teleporter
     */
    public TeleportGate GetTeleporter() {

        Logger.Log(this, "Returning Teleporter");
        Logger.Return();
        return teleporter;

    }

    /**
     * Felfedezetté teszi az aszteroidát.
     * Átállítja az isDiscovered tagváltozót igazra.
     */
    public void Discover(){
        Logger.Log(this, "Discovering Asteroid");
        isDiscovered=true;
        Logger.Return();
    }
    /**
     * Beállítja a teleportert.
     * @param teleportGate az aszteroida új teleportere
     */
    public void SetTeleporter(TeleportGate teleportGate) {

        Logger.Log(this, "Setting teleporter to: " + Logger.GetName(teleportGate));
        teleporter = teleportGate;
        Logger.Return();

    }

}
