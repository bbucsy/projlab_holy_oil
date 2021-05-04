package hu.holyoil.view.popupmenus;

import javax.swing.*;
import java.awt.event.MouseEvent;

public abstract class AbstractPopupMenu extends JPopupMenu {
    protected abstract void InitListeners();

    public void Show(MouseEvent e) {
        setLocation(e.getX(), e.getY());
        setVisible(true);
    }

    public AbstractPopupMenu() {
        super();
    }
}
