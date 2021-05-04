package hu.holyoil.view.popupmenus;

import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * Egy teleportkapura történő kattintáshoz köthető popupmenu-t hozza létre.
 */
public class TeleportGatePopupMenu extends AbstractPopupMenu {

    @Override
    protected void InitListeners() {

    }

    /**
     * Egy teleportkapura történő jobb és bal kattintást kezeli le.
     * @param teleportGate: a teleportkapu amire a settler kattintott
     * @param settler: a settler aki épp játékban van
     * @param e
     */
    public TeleportGatePopupMenu(TeleportGate teleportGate, Settler settler, MouseEvent e) {
        setVisible(false);

        if(e.getButton() == 1){
            lClick(teleportGate);
        }else if(e.getButton() == 3){
            rClick(settler, teleportGate);
        }
    }

    /**
     * A bal egérgombbal történő kattintást kezeli le.
     * @param teleportGate: az teleportkapu amire a játékos kattintott
     *
     * Kilistázza a popupmenu-re a kiválasztott teleportkapu és a túloldalán található asteroid összes fontos információját.
     */
    public void lClick(TeleportGate teleportGate) {
        Asteroid asteroid = teleportGate.GetPair().GetHomeAsteroid();

        //létrehozzuk a kiírandó stringeket
        String crazyString = "Crazy?: " + teleportGate.GetIsCrazy();
        String idString = " ID: " + asteroid.GetId();
        String coreString = " Core: " + asteroid.GetResource().toString();
        String layersString = " Layers: " + asteroid.GetLayerCount();
        String shipsString = " Ships: ";
        for (AbstractSpaceship sp : asteroid.GetSpaceships()) {
            shipsString.concat(" " + sp.GetId());
        }
        String nearSunString = " NearSun?: ";
        if (asteroid.GetIsNearbySun()) {
            nearSunString += "true";
        } else {
            nearSunString += "false";
        }

        //hozzáadjuk a stringeket a popupmenu-höz
        this.add(crazyString);
        this.add("To asteroid:");
        this.add(idString);
        this.add(coreString);
        this.add(layersString);
        this.add(shipsString);
        this.add(nearSunString);
    }

    /**
     * A jobb egérgombbal történő kattintást kezeli le.
     * @param settler: a settler aki épp játékban van
     * @param teleporter: az teleportkapu amire a játékos kattintott
     *
     * Létrehoz egy menüelemet és hozzárendel egy moveListener actionlistenert.
     */
    public void rClick(Settler settler, TeleportGate teleporter){
        JMenuItem travel = new JMenuItem("travel here");
        travel.addActionListener(new moveListener(settler, teleporter));
        this.add(travel);
    }

    /**
     * Egy actionlistener, amely feladata hogy érzékelje amikor rákattintottak e majd végrehajtsa a mozgás műveletet.
     */
    public static class moveListener implements ActionListener {
        TeleportGate teleporter;
        Settler settler;

        /**
         * Az actionlistener konstruktora.
         * @param s: a settler aki épp játékban van
         * @param tg: a teleportkapu amire a játékos kattintott
         */
        public moveListener(Settler s, TeleportGate tg){
            settler = s;
            teleporter = tg;
        }

        /**
         * A megadott teleportkapura meghvja a settler mozgás műveletét.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            settler.Move(teleporter);
        }
    }
}
