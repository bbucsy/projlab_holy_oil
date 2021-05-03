package hu.holyoil.view.popupmenus;

import com.sun.org.apache.xml.internal.security.Init;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class SettlerActionPopupMenu extends AbstractPopupMenu {
    PopupMenu actionPopupMenu;
    MenuItem mine, drill;

    @Override
    protected void InitListeners() {

    }

    public SettlerActionPopupMenu(Settler settler, MouseEvent e) {
        //megnézzük hogy a settler mely asteroidon van
        Asteroid asteroid = settler.GetOnAsteroid();

        //létrehozzuk a popupmenu-t
        actionPopupMenu = new PopupMenu();

        //amennyiben az asteroidnak még van kérge, csak fúrható
        if (asteroid.GetLayerCount() > 0) {
            mine = new MenuItem("mine");
            mine.addActionListener(new mineListener(asteroid, settler));
            actionPopupMenu.add(mine);
        } else {
            if (asteroid.GetResource() != null) {
                drill = new MenuItem("drill");
                drill.addActionListener(new drillListener(asteroid, settler));
                actionPopupMenu.add(drill);
            }
        }

    }

    public class mineListener implements ActionListener {
        Asteroid asteroid;
        Settler settler;

        mineListener(Asteroid a, Settler s) {
            asteroid = a;
            settler = s;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            asteroid.ReactToDrill(settler);
        }
    }

    public class drillListener implements ActionListener {
        Asteroid asteroid;
        Settler settler;

        drillListener(Asteroid a, Settler s) {
            asteroid = a;
            settler = s;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            asteroid.ReactToMineBy(settler, settler.GetStorage());
        }
    }
}
