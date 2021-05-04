package hu.holyoil.view.popupmenus;

import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.AbstractBaseResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaceResourcePopupMenu extends AbstractPopupMenu {
    PopupMenu inventoryPopupMenu;
    MenuItem place;

    @Override
    protected void InitListeners() {
    }

    public PlaceResourcePopupMenu(Settler settler, AbstractBaseResource resource){
        //létrehozzuk a popupmenu-t
        inventoryPopupMenu = new PopupMenu();

        //amennyiben a settler asteroidja üres, csak akkor tud elhelyezni benne nyersanyagot
        if(settler.GetOnAsteroid().GetResource() == null){
            place = new MenuItem("Place resource here");
            place.addActionListener(new inventoryListener(settler, resource));
            inventoryPopupMenu.add(place);
        }
    }

    public class inventoryListener implements ActionListener {
        Settler settler;
        AbstractBaseResource resource;

        public inventoryListener(Settler s, AbstractBaseResource abr){
            settler = s;
            resource = abr;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            settler.PlaceResource(resource);
        }
    }
}
