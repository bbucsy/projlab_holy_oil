package hu.holyoil.view.popupmenus;

import hu.holyoil.controller.TurnController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Egy a settler saját asteroira történő kattintáshoz köthető popupmenu-t hozza létre.
 */
public class SettlerActionPopupMenu extends AbstractPopupMenu {
    /**
     * Egy a settler a saját asteroidjára történő jobb és bal kattintást kezeli le.
     *
     * @param e
     */
    public SettlerActionPopupMenu(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lClick(TurnController.GetInstance().GetSteppingSettler().GetOnAsteroid());
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rClick();
        }
    }

    /**
     * A bal egérgombbal történő kattintást kezeli le.
     * <p>
     * Kilistázza a popupmenu-re a kiválasztott asteroid összes fontos információját.
     * </p>
     *
     * @param asteroid: az asteroid amire a játékos kattintott
     */
    public void lClick(Asteroid asteroid) {
        AsteroidPopupMenu.AddAsteroidInfoToPopupMenu(asteroid, this);
    }

    /**
     * A jobb egérgombbal történő kattintást kezeli le.
     *
     * <p>
     * Amennyiben az asteroidnak van még kérge, a 'drill' művelet jelenik meg a popupmenun.
     * Amennyiben az asteroidnak nincs kérge és a magja nem üreges, a 'mine' művelet jelenik meg.
     * Amennyiben az asteroidnak nincs kérge és a magja üreges, nem jelenik meg művelet.
     * </p>
     */
    public void rClick() {
        JMenuItem drill = new JMenuItem("> Drill Crust!");
        drill.addActionListener(e -> TurnController.GetInstance().GetSteppingSettler().Drill());

        JMenuItem mine = new JMenuItem("> Mine Resource!");
        mine.addActionListener(e -> TurnController.GetInstance().GetSteppingSettler().Mine());

        this.add(drill);
        this.add(mine);

        Settler settler = TurnController.GetInstance().GetSteppingSettler();

        drill.setEnabled(settler.GetOnAsteroid().GetLayerCount() != 0);
        mine.setEnabled(settler.GetOnAsteroid().GetLayerCount() <= 0 && settler.GetOnAsteroid().GetResource() != null);
    }
}
