package hu.holyoil.view.popupmenus;

import com.sun.org.apache.xml.internal.security.Init;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.neighbour.TeleportGate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * Egy a settler saját asteroira történő kattintáshoz köthető popupmenu-t hozza létre.
 */
public class SettlerActionPopupMenu extends AbstractPopupMenu {

    @Override
    protected void InitListeners() {
    }

    /**
     * Egy a settler a saját asteroidjára történő jobb és bal kattintást kezeli le.
     * @param settler: a settler aki épp játékban van
     * @param e
     */
    public SettlerActionPopupMenu(Settler settler, MouseEvent e) {

        setVisible(false);

        if(e.getButton() == 1){
            lClick(settler.GetOnAsteroid());
        }else if(e.getButton() == 3){
            rClick(settler);
        }
    }

    /**
     * A bal egérgombbal történő kattintást kezeli le.
     * @param asteroid: az asteroid amire a játékos kattintott
     *
     * Kilistázza a popupmenu-re a kiválasztott asteroid összes fontos információját.
     */
    public void lClick(Asteroid asteroid){
        //létrehozzuk a kiírandó stringeket
        String idString = "ID: " + asteroid.GetId();
        String coreString = "Core: " + asteroid.GetResource().toString();
        String layersString = "Layers: " + asteroid.GetLayerCount();
        String shipsString = "Ships: ";
        for(AbstractSpaceship sp : asteroid.GetSpaceships()){
            shipsString.concat(" " + sp.GetId());
        }
        String nearSunString = "NearSun?: ";
        if(asteroid.GetIsNearbySun()){
            nearSunString += "true";
        }else{
            nearSunString += "false";
        }

        //hozzáadjuk a popupmenuhöz a menüelemeket
        this.add(idString);
        this.add(coreString);
        this.add(layersString);
        this.add(shipsString);
        this.add(nearSunString);
    }

    /**
     * A jobb egérgombbal történő kattintást kezeli le.
     * @param settler: a settler aki épp játékban van
     *
     * Amennyiben az asteroidnak van még kérge, a 'drill' művelet jelenik meg a popupmenun.
     * Amennyiben az asteroidnak nincs kérge és a magja nem üreges, a 'mine' művelet jelenik meg.
     * Amennyiben az asteroidnak nincs kérge és a magja üreges, nem jelenik meg művelet.
     */
    public void rClick(Settler settler){

        Asteroid asteroid = settler.GetOnAsteroid();

        //amennyiben az asteroidnak még van kérge, csak fúrható
        if (asteroid.GetLayerCount() > 0) {
            JMenuItem drill = new JMenuItem("drill");
            drill.addActionListener(new drillListener(settler));
            this.add(drill);
        } else {
            //amenniyben az asteroidnak nincs kérge és nem üreges a magja akkor bányászható
            if (asteroid.GetResource() != null) {
                JMenuItem mine = new JMenuItem("mine");
                mine.addActionListener(new mineListener(settler));
                this.add(mine);
            }
        }
    }

    /**
     * Egy actionlistener, amely feladata hogy érzékelje amikor rákattintottak e majd végrehajtsa a bányászás műveletet.
     */
    public static class mineListener implements ActionListener {
        Settler settler;

        /**
         * Az actionlistener konstruktora.
         * @param s: a settler aki épp játékban van
         */
        public mineListener(Settler s) {
            settler = s;
        }

        /**
         * Meghívja a settler mine műveletét.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            settler.Mine();
        }
    }

    /**
     * Egy actionlistener, amely feladata hogy érzékelje amikor rákattintottak e majd végrehajtsa a fúrás műveletet.
     */
    public static class drillListener implements ActionListener {
        Settler settler;

        /**
         * Az actionlistener konstruktora.
         * @param s: a settler aki épp játékban van
         */
        public drillListener(Settler s) {
            settler = s;
        }

        /**
         * Meghívja a settler drill műveletét.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            settler.Drill();
        }
    }
}
