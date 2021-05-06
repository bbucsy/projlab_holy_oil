package hu.holyoil.view.popupmenus;

import hu.holyoil.controller.TurnController;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Egy teleportkapura történő kattintáshoz köthető popupmenu-t hozza létre.
 */
public class TeleportGatePopupMenu extends AbstractPopupMenu {
    /**
     * Egy teleportkapura történő jobb és bal kattintást kezeli le.
     * @param teleportGate: a teleportkapu amire a settler kattintott
     * @param e
     */
    public TeleportGatePopupMenu(TeleportGate teleportGate, MouseEvent e) {
        if(e.getButton() == 1){
            lClick(teleportGate);
        }else if(e.getButton() == 3){
            rClick(teleportGate);
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
        //hozzáadjuk a stringeket a popupmenu-höz
        this.add(crazyString);

        if (asteroid!=null) {
            this.add("To asteroid:");
            String idString = " ID: " + asteroid.GetId();
            String coreString;
            if (asteroid.GetResource() != null) {
                coreString = " Core: " + asteroid.GetResource().toString();
            } else {
                coreString = " Core: ";
            }
            String layersString = " Layers: " + asteroid.GetLayerCount();
            StringBuilder shipsString = new StringBuilder(" Ships: ");
            for (AbstractSpaceship sp : asteroid.GetSpaceships()) {
                shipsString.append(" ").append(sp.GetId());
            }
            String nearSunString = " NearSun?: ";
            if (asteroid.GetIsNearbySun()) {
                nearSunString += "true";
            } else {
                nearSunString += "false";
            }
            this.add(idString);
            this.add(coreString);
            this.add(layersString);
            this.add(shipsString.toString());
            this.add(nearSunString);
        }



    }

    /**
     * A jobb egérgombbal történő kattintást kezeli le.
     * @param teleporter: az teleportkapu amire a játékos kattintott
     *
     * Létrehoz egy menüelemet és hozzárendel egy moveListener actionlistenert.
     */
    public void rClick(TeleportGate teleporter){
        JMenuItem travel = new JMenuItem("travel here");
        travel.addActionListener(e -> TurnController.GetInstance().GetSteppingSettler().Move(teleporter));
        this.add(travel);
    }
}
