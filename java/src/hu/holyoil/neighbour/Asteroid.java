package hu.holyoil.neighbour;

import hu.holyoil.controller.Logger;
import hu.holyoil.crewmate.AbstractCrewmate;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.IMiner;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.repository.AsteroidRepository;
import hu.holyoil.repository.NeighbourBaseRepository;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.storage.PlayerStorage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Aszteroidákat leíró osztály.
 * Implementálja az INeighbour interfacet.
 */
public class Asteroid implements INeighbour {

    /**
     * Az aszteroida egyedi azonosítója
     * */
    private String id;

    /**
     * Visszaadja az aszteroida egyedi azonosítóját.
     * */
    public String GetId() {
        return id;
    }

    /**
     * Meghívja a konstruktort egy generált id-vel, amennyiben az nincs előre meghatározva.
     */
    public Asteroid() {

        this(NeighbourBaseRepository.GetIdWithPrefix("Asteroid"));

    }

    /**
     * Konstruktor
     * Inicializálja a tagváltozókat, a listákat üresen.
     * Az aszteroida létrehozáskor a magja üres, nincs napközelben, nincs felfedezve a játékosok által, a kérgének vastagsága 0 valamint nincs rajta teleporter.
     */
    public Asteroid(String name) {

        neighbouringAsteroids = new ArrayList<>();
        spaceships = new ArrayList<>();
        resource = null;
        teleporter = null;
        isNearSun = Boolean.FALSE;
        isDiscovered = Boolean.FALSE;
        numOfLayersRemaining = 0;
        id = name;
        NeighbourBaseRepository.GetInstance().Add(name, this);

    }

    /**
     * Kiírja az aszteroidát emberileg olvasható módon. Az asszociációk helyén azonosítók vannak.
     * */
    @Override
    public String toString() {

        StringBuilder toReturn = new StringBuilder("ASTEROID (name:) " + id
                + "\n\t(is near sun:) " + isNearSun
                + "\n\t(layers left:) " + numOfLayersRemaining
                + "\n\t(is discovered:) " + isDiscovered
                + "\n\t(resource name:) " + (resource == null ? "null" : resource.GetId())
                + "\n\t(teleporter name:) " + (teleporter == null ? "null" : teleporter.GetId()));

        toReturn.append("\n\t(spaceship names:) [");
        for (int i = 0; i < spaceships.size(); i++) {
            toReturn.append(spaceships.get(i).GetId());
            if (i != spaceships.size() - 1) {
                toReturn.append(" ");
            }
        }
        toReturn.append("]\n\t(neighbour asteroid names:) [");
        for (int i = 0; i < neighbouringAsteroids.size(); i++) {
            toReturn.append(neighbouringAsteroids.get(i).GetId());
            if (i != neighbouringAsteroids.size() - 1) {
                toReturn.append(" ");
            }
        }
        toReturn.append("]");

        return toReturn.toString();

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
     * Akkor igaz, amennyiben az aszteroida már fel lett fedezvea játékosok által.
     */
    public Boolean IsDiscovered() {
        return isDiscovered;
    }

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
        from.RemoveSpaceship(abstractSpaceship);
        AddSpaceship(abstractSpaceship);
        abstractSpaceship.SetOnAsteroid(this);

        abstractSpaceship.ReactToMoveMade();
    }

    /**
     * Mozgatja a kapott teleportert erre az aszteroidára.
     * @param comingTeleporter az ide mozgást elvégezni készülő teleporter
     */
    public void ReactToMove(TeleportGate comingTeleporter){
        if (teleporter == null) {
            comingTeleporter.GetHomeAsteroid().RemoveTeleporter();
            SetTeleporter(comingTeleporter);
            comingTeleporter.SetHomeAsteroid(this);
        }
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
        if (numOfLayersRemaining == 0 && resource == null) {
            res.ReactToPlace(this, s);
        }
    }

    /**
     * Beállítja a napközelséget jelző logikai változót
     * <p>
     *     Ezt a SunController képes megtenni egy lépéssel.
     * </p>
     * @param newIsNearbySun új logikai értéke a napközelségnek, lehet igaz és hamis is
     */
    public void SetIsNearbySun(Boolean newIsNearbySun) {
        isNearSun = newIsNearbySun;
    }

    /**
     * Visszaadja napközeli-e az aszteroida
     * @return boolean, true: napközeli, false: nem napközeli
     */
    public boolean GetIsNearbySun(){
        return isNearSun;
    }

    /**
     * Beállítja a még magig fúrandó kéregvastagságot.
     * @param newNumOfLayersRemaining új értéke a kéreg vastagsnak
     */
    public void SetNumOfLayersRemaining(int newNumOfLayersRemaining) {
        numOfLayersRemaining = newNumOfLayersRemaining;
    }

    /**
     * Eggyel csökkenti a magig fúrandó réteg vastagságát.
     * <p>Erre egy Spaceship képes egy fúrás lépéssel.</p>
     */
    public void DecNumOfLayersRemaining() {
        if (numOfLayersRemaining > 0)
            numOfLayersRemaining--;
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
        if (resource != null && numOfLayersRemaining == 0) {
            resource.ReactToMine(this, iMiner);
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
        if (resource != null && numOfLayersRemaining == 0) {
            resource.ReactToMine(this, iMiner, storage);
        }
    }

    /**
     * Az aszteroida reagál ha egy Spaceship megpróbál rajta fúrni.
     * <p>
     *     Ha még van hátra fúrnivaló kéreg eggyel csökkenti a kéreg vastagságát, egyébként nem történik semmi.
     * </p>
     */
    public void ReactToDrill(AbstractCrewmate abstractCrewmate) {
        if (numOfLayersRemaining>= 1) {
            this.DecNumOfLayersRemaining();
            abstractCrewmate.ReactToMoveMade();
        }
    }

    /**
     * Az szteroida reagál a napviharra.
     * <p>
     *     Ha az aszteroida magja NEM üres VAGY az aszteroida kérge még nincs teljesen kifúrva:
     *     az összes Spaceship meghal a spaceships listán.
     * </p>
     */
    public void ReactToSunstorm() {
        if (numOfLayersRemaining > 0 || resource != null) {
            KillAllSpaceships();
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
        if (isNearSun && numOfLayersRemaining == 0 && resource != null) {
            resource.ReactToSunNearby(this);
        }
    }

    /**
     * Visszaadja a magban található nyersanyagot.
     * @return visszaadja a resource tagváltozót
     */
    public AbstractBaseResource GetResource(){
        return resource;
    }

    /**
     * Beállítja a magban található nyersanyagot.
     * @param abstractBaseResource erre a nyersanyagra állítódik a mag
     */
    public void SetResource(AbstractBaseResource abstractBaseResource) {
        resource = abstractBaseResource;
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
     *     Ha kikapcsolt a randomizálás:
     *     Kapunk aszteroidát.
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
     * @return véletlen szomszéd
     */
    public INeighbour GetRandomNeighbour() {
        Random random = new Random();
        boolean canChooseTeleporter = false;
        if (teleporter != null) {
            if (teleporter.GetPair().GetHomeAsteroid() != null) {
                canChooseTeleporter = true;
            }
        }

        int chosenIndex = random.nextInt((neighbouringAsteroids.size() + (canChooseTeleporter ? 1 : 0)));

        return chosenIndex == neighbouringAsteroids.size() ?
                teleporter :
                neighbouringAsteroids.get(chosenIndex);
    }

    /**
     * Hozzáad egy új szomszédot az aszteroidának, ha az még nem volt hozzáadva
     * @param asteroid az új szomszéd
     */
    public void AddNeighbourAsteroid(Asteroid asteroid) {
        if (neighbouringAsteroids.contains(asteroid)) {
            return;
        }
        neighbouringAsteroids.add(asteroid);
    }

    /**
     * Visszaadja a szomszédok listáját.
     * @return a neighbouringAsteroids lista
     */
    public List<Asteroid> GetNeighbours() {
        return neighbouringAsteroids;
    }

    /**
     * Felvesz egy új Spaceshipt az aszteroidára
     * @param abstractSpaceship az aszteroidára lépő telepes vagy robot
     */
    public void AddSpaceship(AbstractSpaceship abstractSpaceship) {
        spaceships.add(abstractSpaceship);
    }

    /**
     * Elvesz egy Spaceshipt az aszteroidáról
     * @param abstractSpaceship a törlendő telepes vagy robot
     */
    public void RemoveSpaceship(AbstractSpaceship abstractSpaceship) {
        spaceships.remove(abstractSpaceship);
    }

    /**
     * Megöli az aszteroidán tartózkodó összes egységet.
     */
    public void KillAllSpaceships() {
        List<AbstractSpaceship> abstractSpaceshipsShallowCopy = new LinkedList<>(spaceships);
        abstractSpaceshipsShallowCopy.forEach(AbstractSpaceship::Die);
    }

    /**
     * Törli az aszteroidán található teleportert.
     */
    public void RemoveTeleporter() {
        teleporter = null;
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
        List<AbstractSpaceship> spaceshipsShallowCopy = new ArrayList<>(spaceships);
        spaceshipsShallowCopy.forEach(AbstractSpaceship::ReactToAsteroidExplosion);
        neighbouringAsteroids.forEach(
                asteroid -> asteroid.RemoveNeighbouringAsteroid(this)
        );

        if (teleporter != null) {
            teleporter.Explode();
        }
        AsteroidRepository.GetInstance().Remove(id);
        if (resource != null) {
            resource.ReactToGettingDestroyed();
        }

        if (isDiscovered) {
            Logger.Log(this, "Exploded");
        }
    }

    /**
     * ELtávolít egy aszteroidát ennek a szomszédságából
     * @param asteroid az eltávolítandó aszteroida
     * */
    public void RemoveNeighbouringAsteroid(Asteroid asteroid) {
        neighbouringAsteroids.remove(asteroid);
    }

    /**
     * Visszaadja az aszteroidán található teleportert.
     * <p>
     *     Ez lehet null, ha az aszteroidán nincs teleporter.
     * </p>
     * @return null, vagy az aszteroidán található teleporter
     */
    public TeleportGate GetTeleporter() {
        return teleporter;
    }

    /**
     * Felfedezetté teszi az aszteroidát.
     * Átállítja az isDiscovered tagváltozót igazra.
     */
    public void Discover(){
        isDiscovered=true;
    }
    /**
     * Beállítja a teleportert.
     * @param teleportGate az aszteroida új teleportere
     */
    public void SetTeleporter(TeleportGate teleportGate) {
        teleporter = teleportGate;
    }

    /**
     * Beállítja az új felfedezettséget
     * @param newIsDiscovered az isDiscovered új értéke
     */
    public void SetIsDiscovered(Boolean newIsDiscovered) {
        isDiscovered = newIsDiscovered;
    }

    /**
     * Visszadja az átfúratlan rétegek számát
     * @return az átfúratlan rétegek száma
     */
    public int GetLayerCount(){
        return numOfLayersRemaining;
    }

    /**
     * Visszaadja az aszteroidán lévő entitásokat
     * @return aszteroida foglalói
     */
    public List<AbstractSpaceship> GetSpaceships() {
        return spaceships;
    }
}
