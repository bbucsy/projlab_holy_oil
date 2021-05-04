package hu.holyoil.view.popupmenus;

import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * Egy a settler sajátjától különböző asteroira történő kattintáshoz köthető popupmenu-t hozza létre.
 */
public class AsteroidPopupMenu extends AbstractPopupMenu {

    @Override
    protected void InitListeners() {

    }


    /**
     * Egy a settler sajátjától különböző asteroidra történő jobb és bal kattintást kezeli le.
     * @param asteroid: az asteroid amire a játékos kattintott
     * @param settler: a settler aki épp játékban van
     * @param e
     */
    public AsteroidPopupMenu(Asteroid asteroid, Settler settler, MouseEvent e) {

        setVisible(false);

        if(e.getButton() == 1){
            lClick(asteroid);
        }else if(e.getButton() == 3){
            rClick(settler, asteroid);
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
        String coreString;
        if(asteroid.GetResource() != null) {
            coreString = "Core: " + asteroid.GetResource().toString();
        }else{
            coreString = "Core: ";
        }
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

        //hozzáadjuk a stringeket a popupmenu-höz
        this.add(idString);
        this.add(coreString);
        this.add(layersString);
        this.add(shipsString);
        this.add(nearSunString);
    }

    /**
     * A jobb egérgombbal történő kattintást kezeli le.
     * @param settler: a settler aki épp játékban van
     * @param asteroid: az asteroid amire a játékos kattintott
     *
     * Létrehoz egy menüelemet és hozzárendel egy moveListener actionlistenert.
     */
    public void rClick(Settler settler, Asteroid asteroid){
        JMenuItem travel = new JMenuItem("travel here");
        travel.addActionListener(new moveListener(settler, asteroid));
        this.add(travel);
    }

    /**
     * Egy actionlistener, amely feladata hogy érzékelje amikor rákattintottak e majd végrehajtsa a mozgás műveletet.
     */
    public static class moveListener implements ActionListener {
        Asteroid asteroid;
        Settler settler;

        /**
         * Az actionlistener konstruktora.
         * @param s: a settler aki épp játékban van
         * @param a: az asteroid amire a játékos kattintott
         */
        public moveListener(Settler s, Asteroid a){
            settler = s;
            asteroid = a;
        }

        /**
         * A megadott asteroid-ra meghvja a settler mozgás műveletét.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            settler.Move(asteroid);
        }
    }
}
