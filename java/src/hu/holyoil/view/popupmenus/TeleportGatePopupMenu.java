package hu.holyoil.view.popupmenus;

import hu.holyoil.controller.TurnController;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Egy teleportkapura történő kattintáshoz köthető popupmenu-t hozza létre.
 */
public class TeleportGatePopupMenu extends AbstractPopupMenu {
    /**
     * Egy teleportkapura történő jobb és bal kattintást kezeli le.
     *
     * @param teleportGate: a teleportkapu amire a settler kattintott
     * @param e
     */
    public TeleportGatePopupMenu(TeleportGate teleportGate, MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lClick(teleportGate);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rClick(teleportGate);
        }
    }

    /**
     * A bal egérgombbal történő kattintást kezeli le.
     *
     * <p>
     * Kilistázza a popupmenu-re a kiválasztott teleportkapu és a túloldalán található asteroid összes fontos információját.
     * </p>
     *
     * @param teleportGate: az teleportkapu amire a játékos kattintott
     */
    public void lClick(TeleportGate teleportGate) {
        Asteroid asteroid = teleportGate.GetPair().GetHomeAsteroid();
        add("ID: " + teleportGate.GetId());

        if (teleportGate.GetIsCrazy()) {
            JMenuItem item = new JMenuItem("Went crazy");
            item.setForeground(new Color(178, 34, 34));
            add(item);
        }

        if (asteroid != null) {
            add("Leading to...");
            AsteroidPopupMenu.AddAsteroidInfoToPopupMenu(asteroid, this);
        }
    }

    /**
     * A jobb egérgombbal történő kattintást kezeli le.
     * <p>
     * Létrehoz egy menüelemet és hozzárendel egy moveListener actionlistenert.
     * </p>
     *
     * @param teleportGate: az teleportkapu amire a játékos kattintott
     */
    public void rClick(TeleportGate teleportGate) {
        JMenuItem travel = new JMenuItem("> Travel here!");
        travel.addActionListener(e -> TurnController.GetInstance().GetSteppingSettler().Move(teleportGate));
        this.add(travel);
        travel.setEnabled(teleportGate.GetPair().GetHomeAsteroid() != null);
    }
}
