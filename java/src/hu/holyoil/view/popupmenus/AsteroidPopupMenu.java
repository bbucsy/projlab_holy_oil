package hu.holyoil.view.popupmenus;

import hu.holyoil.controller.TurnController;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.neighbour.Asteroid;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Egy a settler sajátjától különböző asteroira történő kattintáshoz köthető popupmenu-t hozza létre.
 */
public class AsteroidPopupMenu extends AbstractPopupMenu {
    /**
     * Egy a settler sajátjától különböző asteroidra történő jobb és bal kattintást kezeli le.
     * @param asteroid: az asteroid amire a játékos kattintott
     * @param e mouse event
     */
    public AsteroidPopupMenu(Asteroid asteroid, MouseEvent e) {
        if(e.getButton() == 1){
            lClick(asteroid);
        }else if(e.getButton() == 3){
            rClick(asteroid);
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
        if(!asteroid.IsDiscovered()){
            String discovered = "Not discovered yet";
            this.add(idString);
            this.add(discovered);
            return;
        }
        String coreString;
        if(asteroid.GetResource() != null) {
            coreString = "Core: " + asteroid.GetResource().toString();
        }else{
            coreString = "Core: ";
        }
        String layersString = "Layers: " + asteroid.GetLayerCount();
        StringBuilder shipsString = new StringBuilder("Ships: ");
        for(AbstractSpaceship sp : asteroid.GetSpaceships()){
            shipsString.append(" ").append(sp.GetId());
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
        this.add(shipsString.toString());
        this.add(nearSunString);
    }

    /**
     * A jobb egérgombbal történő kattintást kezeli le.
     * @param asteroid: az asteroid amire a játékos kattintott
     *
     * Létrehoz egy menüelemet és hozzárendel egy moveListener actionlistenert.
     */
    public void rClick(Asteroid asteroid){
        JMenuItem travel = new JMenuItem("travel here");
        travel.addActionListener(e -> {
            TurnController.GetInstance().GetSteppingSettler().Move(asteroid);

        });
        this.add(travel);
    }

}
