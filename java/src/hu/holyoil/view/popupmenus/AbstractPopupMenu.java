package hu.holyoil.view.popupmenus;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Az általános felugró menü ősosztály. Bármivel a kijelzőn interakcióba próbálunk lépni, szinte biztos hogy egy
 * ilyen popup menu-n keresztül fog történni. Az azonos működésű függvényeket ebben az osztályban írjuk meg.
 */
public abstract class AbstractPopupMenu extends JPopupMenu {
    /**
     * Kimutatja a megadott MouseEvent helyén a menüt.
     * @param e mouse event, amellyel a kattintás helye ismert
     */
    public void Show(MouseEvent e) {
        show(e.getComponent(), e.getX(), e.getY());
    }
}
