package hu.holyoil.controller;

import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.skeleton.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A nap viselkedését irányító singleton kontroller osztály. Körönként lép, ezért implementálja az ISteppable interfacet
 * <p>A PROJEKT EZEN FÁZISÁBAN MÉG NINCS KÉSZ, A TESZTELÉSHEZ NEM SZÜKSÉGES. Ennek megfelelően a dokumentáció is csak felszínes megértést nyújt, amennyi a tesztekhez kellhet.</p>
 */
public class SunController implements ISteppable {
    /**
     * A singleton osztályra ezzel lehet hivatkozni
     */
    private static SunController sunController;
    /**
     * Pseudo random szám, ami a következő napviharig hátralévő köröket számolja
     * <p>körönként csökken,napvihar után újra sorsolódik</p>
     */
    private Integer turnsUntilNextSunstorm;
    /**
     * A pályán található összes aszteroidát tartalmazó lista
     */
    private List<Asteroid> asteroids;

    /**
     * minden körben végrehajt egy lépést
     */
    @Override
    public void Step() {
        System.out.println("Stepping");
    }

    /**
     * Hozzáad egy aszteroidát a számontartott aszteroidák listájához
     * @param asteroid hozzáadja az asteroids tagváltozóhoz
     */
    public void AddAsteroid(Asteroid asteroid)  {
        Logger.Log(this,"Adding asteroid <" +  Logger.GetName(asteroid)+ " >");
        asteroids.add(asteroid);
        Logger.Return();
    }

    /**
     * Töröl egy aszteroidát a számontartott aszteroidák listájáról
     * @param asteroid törli az asteroids tagváltozóról
     */
    public void RemoveAsteroid(Asteroid asteroid)  {
        Logger.Log(this,"Removing asteroid <" +  Logger.GetName(asteroid)+ " >");
        asteroids.remove(asteroid);
        Logger.Return();
    }

    /**
     * Amikor a turnsUntilNextSunstorm számláló eléri a 0-t elindít egy napvihart.
     * <p>A pályán található összes aszteroida reagál rá.</p>
     */
    public void StartSunstorm()  {
       Logger.Log(this,"Starting sunstorm");
       asteroids.forEach(Asteroid::ReactToSunstorm);
       Logger.Return();
    }

    /**
     * Singleton osztályhoz való hozzáférés miatt kell
     * @return visszaad egy instance-ot
     */
    public static SunController GetInstance() {

        if (sunController == null) {
            sunController = new SunController();
        }

        return sunController;

    }

    /**
     * Privát konstruktor
     * Nem lehet kívülről meghívni, nem lehet példányosítani.
     * <p>A 100 forduló a következő napviharig egy ad hoc szám, bármi lehet.
     * Inicializálja az aszteroidáka tároló listát.</p>
     */
    private SunController() {
        turnsUntilNextSunstorm = 100;
        asteroids = new ArrayList<>();
    }

}
