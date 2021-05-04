package hu.holyoil.view.popupmenus;

import com.sun.org.apache.xml.internal.security.Init;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.INeighbour;
import hu.holyoil.neighbour.TeleportGate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class SettlerActionPopupMenu extends AbstractPopupMenu {
    PopupMenu actionPopupMenu;
    MenuItem mine, drill, travel;

    @Override
    protected void InitListeners() {
    }

    /**
     *
     * @param settler: a settler, amelyik a kattintáskor épp játékban van
     * @param neighbour: az asteroid/teleportgate amire a játékos kattintott
     * @param e
     * Egy popupmenüt hoz létre, amely lekezeli a következő eseteket (feltételezve hogy ez jobb klikkel történt, az nem is kerül vizsgálatra):
     * - a játékos a saját asteroidjára kattintott, és még annak van kérge -> a popupmenüben csak a drill lehetőség jelenik meg
     * - a játékos a saját asteroidjára kattintott, de annak már nincs kérge de van benne nyersanyag, ekkor a mine lehetőség jelenik meg
     * - a játékos a saját asteroidjára kattintott, de annak nincs kérge és üreges, ekkor a popupmenu üres
     * - a játékos egy szomszédos asteroidra/teleportgatera kattintott, akkor a travel here menüpont jelenik meg
     * Az összes menüelem meghívja a listenerjében a szükséges függvént ami megvalósítja az elvárt funkciót.
     */

    public SettlerActionPopupMenu(Settler settler, INeighbour neighbour, MouseEvent e) {
        //létrehozzuk a popupmenu-t
        actionPopupMenu = new PopupMenu();

        //a drill és mine lehetőség csak akkor bukkanhat fel ha a sajtá asteroidjára kattintott a settler
        if(settler.GetOnAsteroid().GetId().equals(neighbour.GetId())) {
            Asteroid asteroid = (Asteroid) neighbour;
            //amennyiben az asteroidnak még van kérge, csak fúrható
            if (asteroid.GetLayerCount() > 0) {
                mine = new MenuItem("mine");
                mine.addActionListener(new mineListener(settler));
                actionPopupMenu.add(mine);
            } else {
                //amenniyben az asteroidnak nincs kérge és nem üreges a magja akkor bányászható
                if (asteroid.GetResource() != null) {
                    drill = new MenuItem("drill");
                    drill.addActionListener(new drillListener(settler));
                    actionPopupMenu.add(drill);
                }
            }
        }else{
            travel = new MenuItem("travel here");
            travel.addActionListener(new moveListener(settler, neighbour));
            actionPopupMenu.add(travel);
        }
    }

    public static class mineListener implements ActionListener {
        Settler settler;

        public mineListener(Settler s) {
            settler = s;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            settler.Mine();
        }
    }

    public static class drillListener implements ActionListener {
        Settler settler;

        public drillListener(Settler s) {
            settler = s;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            settler.Drill();
        }
    }

    public static class moveListener implements ActionListener{
        INeighbour neighbour;
        Settler settler;

        public moveListener(Settler s, INeighbour n){
            settler = s;
            neighbour = n;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            settler.Move(neighbour);
        }
    }
}
