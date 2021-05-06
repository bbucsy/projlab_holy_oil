package hu.holyoil.view.frames;

import hu.holyoil.view.IViewComponent;
import hu.holyoil.view.panels.EnvironmentPanel;
import hu.holyoil.view.panels.InventoryListPanel;
import hu.holyoil.view.panels.LogPanel;
import hu.holyoil.view.panels.PlayerListPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * A teljes játékablakot létrehozó osztály. Ő tárol egy a játékosokat listázó panelt, egy a settlernél lévő
 * nyersanyagokat listázó panelt, egy a pályát megjelenítő panelt és egy az utolsó kör végén történt eseményeket
 * listázó panelt. Mindig a pillanatnyilag játékban lévő telepeshez tartozó információt jeleníti meg.
 */
public class GameFrame extends JFrame implements IViewComponent {
    /**
     * A keretet felépítő panelek gyűjtője
     */
    private List<IViewComponent> panels;

    private void InitComponent() {
        LogPanel logPanel = new LogPanel();
        EnvironmentPanel environmentPanel = new EnvironmentPanel();
        InventoryListPanel inventoryListPanel = new InventoryListPanel();
        PlayerListPanel playerListPanel = new PlayerListPanel();
        panels = Arrays.asList(logPanel, environmentPanel, inventoryListPanel, playerListPanel);

        JPanel mainPanel = new JPanel();
        GroupLayout mainLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainLayout);
        mainPanel.setOpaque(false);

        mainLayout.setHorizontalGroup(
                mainLayout.createSequentialGroup()
                        .addComponent(environmentPanel)
                        .addGroup(
                                mainLayout.createParallelGroup()
                                        .addComponent(inventoryListPanel)
                                        .addComponent(playerListPanel)
                        )
        );
        mainLayout.setVerticalGroup(
                mainLayout.createSequentialGroup()
                        .addGroup(
                                mainLayout.createParallelGroup()
                                        .addComponent(environmentPanel)
                                        .addGroup(
                                                mainLayout.createSequentialGroup()
                                                        .addComponent(inventoryListPanel)
                                                        .addComponent(playerListPanel)
                                        )
                        )
        );

        getContentPane().setBackground(Color.black);
        add(mainPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.SOUTH);
    }

    @Override
    public void UpdateComponent() {
        panels.forEach(IViewComponent::UpdateComponent);
    }

    public GameFrame() {
        super();
        setTitle("Holy Oil Game - in game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InitComponent();
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
}
