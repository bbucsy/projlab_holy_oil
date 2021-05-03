package hu.holyoil.view.popupmenus;

import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.neighbour.Asteroid;

import java.awt.*;
import java.awt.event.MouseEvent;

public class AsteroidPopupMenu extends AbstractPopupMenu {
    PopupMenu asteroidPopupMenu;

    @Override
    protected void InitListeners() {

    }

    public AsteroidPopupMenu(Asteroid asteroid, MouseEvent e) {
        //létrehozzuk a kiírandó stringeket
        String idString = "ID: " + asteroid.GetId();
        String coreString = "Core: " + asteroid.GetResource().toString();
        String layersString = "Layers: " + asteroid.GetLayerCount();
        String shipsString = "Ships: ";
        for(AbstractSpaceship sp : asteroid.GetSpaceships()){
            shipsString.concat(" " + sp.GetId());
        }
        String nearSunString = "NearSun?: ";
        if(asteroid.GetIsNearbySun()){
            nearSunString += "true";
        }else{
            nearSunString += "false";
        }

        //létrehozzuk a popupmenüt és hozzáadjuk a menüelemeket
        asteroidPopupMenu = new PopupMenu();
        asteroidPopupMenu.add(idString);
        asteroidPopupMenu.add(coreString);
        asteroidPopupMenu.add(layersString);
        asteroidPopupMenu.add(shipsString);
        asteroidPopupMenu.add(nearSunString);
    }
}
