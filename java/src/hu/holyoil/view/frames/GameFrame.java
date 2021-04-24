package hu.holyoil.view.frames;

import hu.holyoil.view.IViewComponent;

import javax.swing.*;

public class GameFrame extends JFrame implements IViewComponent {
    private void InitComponent() {

    }

    private void InitListeners() {

    }

    @Override
    public void UpdateComponent() {

    }

    public GameFrame() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InitComponent();
        InitListeners();
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
    }
}
