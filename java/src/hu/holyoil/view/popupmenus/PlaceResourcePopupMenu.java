package hu.holyoil.view.popupmenus;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.AbstractBaseResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Amikor a játékos egy nyersanyagot próbál lehelyezni, az ahhoz tatozó popupmenu-t hozza létre.
 */
public class PlaceResourcePopupMenu extends AbstractPopupMenu {

    @Override
    protected void InitListeners() {
    }

    /**
     * Egy konstruktor amely létrahozza a popupmenut és feltölti.
     * @param settler: a settler aki épp játékban van
     * @param resource: a nyersanyag amelyet a játékos kiválasztott
     */
    public PlaceResourcePopupMenu(Settler settler, AbstractBaseResource resource){

        setVisible(false);

        //amennyiben a settler asteroidja üres, csak akkor tud elhelyezni benne nyersanyagot
        if(settler.GetOnAsteroid().GetResource() == null){
            JMenuItem place = new JMenuItem("Place resource here");
            place.addActionListener(new inventoryListener(settler, resource));
            this.add(place);
        }
    }

    /**
     * Egy actionlistener, amely feladata hogy érzékelje amikor rákattintottak e majd végrehajtsa a nyersanyag lehelyezés műveletet.
     */
    public class inventoryListener implements ActionListener {
        Settler settler;
        AbstractBaseResource resource;

        /**
         * Az actionlistener konstruktora.
         * @param s: a settler aki épp játékban van
         * @param abr: a nyersanyag amelyet a játékos kiválasztott
         */
        public inventoryListener(Settler s, AbstractBaseResource abr){
            settler = s;
            resource = abr;
        }

        /**
         * Meghívja a settler nyersanyagot lehelyező műveletét az adott nyersanyaggal.
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            settler.PlaceResource(resource);
        }
    }
}
