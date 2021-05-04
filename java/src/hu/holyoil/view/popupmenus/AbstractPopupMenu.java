package hu.holyoil.view.popupmenus;

import javax.swing.*;
import java.awt.event.MouseEvent;

public abstract class AbstractPopupMenu extends JPopupMenu {
    public void Show(MouseEvent e) {
        show(e.getComponent(), e.getX(), e.getY());
    }

    public AbstractPopupMenu() {
        super();
    }
}
