package hu.holyoil.view.popupmenus;

import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TeleportGatePopupMenu extends AbstractPopupMenu {
    PopupMenu teleportPopupMenu;

    @Override
    protected void InitListeners() {

    }

    public TeleportGatePopupMenu(TeleportGate teleportGate, MouseEvent e) {
        Asteroid asteroid = teleportGate.GetPair().GetHomeAsteroid();

        //létrehozzuk a kiírandó stringeket
        String crazyString = "Crazy?: " + teleportGate.GetIsCrazy();
        String idString = " ID: " + asteroid.GetId();
        String coreString = " Core: "+ asteroid.GetResource().toString();
        String layersString = " Layers: " + asteroid.GetLayerCount();
        String shipsString = " Ships: ";
        for(AbstractSpaceship sp : asteroid.GetSpaceships()){
            shipsString.concat(" " + sp.GetId());
        }
        String nearSunString = " NearSun?: ";
        if(asteroid.GetIsNearbySun()){
            nearSunString += "true";
        }else{
            nearSunString += "false";
        }

        //létrehozzuk a popupmenüt és hozzáadjuk a menüelemeket
        teleportPopupMenu = new PopupMenu();
        teleportPopupMenu.add(crazyString);
        teleportPopupMenu.add("To asteroid:");
        teleportPopupMenu.add(idString);
        teleportPopupMenu.add(coreString);
        teleportPopupMenu.add(layersString);
        teleportPopupMenu.add(shipsString);
        teleportPopupMenu.add(nearSunString);
    }
}
