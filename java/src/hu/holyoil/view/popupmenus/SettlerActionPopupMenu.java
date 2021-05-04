package hu.holyoil.view.popupmenus;

import com.sun.org.apache.xml.internal.security.Init;
import hu.holyoil.commandhandler.Logger;
import hu.holyoil.controller.TurnController;
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
    /**
     * Egy a settler a saját asteroidjára történő jobb és bal kattintást kezeli le.
     * @param e
     */
    public SettlerActionPopupMenu(MouseEvent e) {
        if (e.getButton() == 1) {
            lClick(TurnController.GetInstance().GetSteppingSettler().GetOnAsteroid());
        }
        else if (e.getButton() == 3) {
            rClick();
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

        //hozzáadjuk a popupmenuhöz a menüelemeket
        this.add(idString);
        this.add(coreString);
        this.add(layersString);
        this.add(shipsString.toString());
        this.add(nearSunString);
    }

    /**
     * A jobb egérgombbal történő kattintást kezeli le.
     *
     * <p>
     *      Amennyiben az asteroidnak van még kérge, a 'drill' művelet jelenik meg a popupmenun.
     *      Amennyiben az asteroidnak nincs kérge és a magja nem üreges, a 'mine' művelet jelenik meg.
     *      Amennyiben az asteroidnak nincs kérge és a magja üreges, nem jelenik meg művelet.
     * </p>
     */
    public void rClick(){
        JMenuItem drill = new JMenuItem("drill");
        drill.addActionListener(e -> TurnController.GetInstance().GetSteppingSettler().Drill());

        JMenuItem mine = new JMenuItem("mine");
        mine.addActionListener(e -> TurnController.GetInstance().GetSteppingSettler().Mine());

        this.add(drill);
        this.add(mine);

        Settler settler = TurnController.GetInstance().GetSteppingSettler();

        if (settler.GetOnAsteroid().GetLayerCount() == 0) {
            drill.setEnabled(false);
        }

        if (settler.GetOnAsteroid().GetLayerCount() > 0 || settler.GetOnAsteroid().GetResource() == null) {
            mine.setEnabled(false);
        }
    }
}
