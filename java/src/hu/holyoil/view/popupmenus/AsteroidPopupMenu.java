package hu.holyoil.view.popupmenus;

import hu.holyoil.controller.TurnController;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.neighbour.Asteroid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Egy a settler sajátjától különböző asteroira történő kattintáshoz köthető popupmenu-t hozza létre.
 */
public class AsteroidPopupMenu extends AbstractPopupMenu {
    /**
     * Egy a settler sajátjától különböző asteroidra történő jobb és bal kattintást kezeli le.
     *
     * @param asteroid: az asteroid amire a játékos kattintott
     * @param e         mouse event
     */
    public AsteroidPopupMenu(Asteroid asteroid, MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lClick(asteroid);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rClick(asteroid);
        }
    }

    /**
     * Statikus metódus, amely hozzáadja az aszteroida információt a popupmenühöz
     *
     * @param asteroid amelynek az adatait vizsgáljuk
     * @param popupMenu amelyhez hozzáadódnak az információk
     */
    public static void AddAsteroidInfoToPopupMenu(Asteroid asteroid, AbstractPopupMenu popupMenu) {
        popupMenu.add("ID: " + asteroid.GetId());

        if (asteroid.GetResource() != null) {
            popupMenu.add("Core: " + asteroid.GetResource().toString());
        }

        popupMenu.add("Layers: " + asteroid.GetLayerCount());

        if (asteroid.GetSpaceships().size() != 0) {
            StringBuilder shipsString = new StringBuilder("Ships: ");
            for (AbstractSpaceship sp : asteroid.GetSpaceships()) {
                shipsString.append(" ").append(sp.GetId());
            }
            popupMenu.add(shipsString.toString());
        }

        if (asteroid.GetIsNearbySun()) {
            JMenuItem item = new JMenuItem("Near sun");
            item.setForeground(asteroid.GetIsNearbySun() ? new Color(178, 34, 34) : item.getForeground());
            popupMenu.add(item);
        }
    }

    /**
     * A bal egérgombbal történő kattintást kezeli le.
     *
     * <p>
     * Kilistázza a popupmenu-re a kiválasztott asteroid összes fontos információját.
     * </p>
     *
     * @param asteroid: az asteroid amire a játékos kattintott
     */
    public void lClick(Asteroid asteroid) {
        if (!asteroid.IsDiscovered()) {
            add("ID: " + asteroid.GetId());
            this.add("Not discovered yet");
            return;
        }
        AddAsteroidInfoToPopupMenu(asteroid, this);
    }

    /**
     * A jobb egérgombbal történő kattintást kezeli le.
     *
     * @param asteroid: az asteroid amire a játékos kattintott
     *                  <p>
     *                  Létrehoz egy menüelemet és hozzárendel egy moveListener actionlistenert.
     */
    public void rClick(Asteroid asteroid) {
        JMenuItem travel = new JMenuItem("> Travel here!");
        travel.addActionListener(e -> {
            TurnController.GetInstance().GetSteppingSettler().Move(asteroid);

        });
        this.add(travel);
    }

}
