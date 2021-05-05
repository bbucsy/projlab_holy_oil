package hu.holyoil.view.panels;

import hu.holyoil.commandhandler.Logger;
import hu.holyoil.controller.TurnController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.resource.AbstractBaseResource;
import hu.holyoil.storage.PlayerStorage;
import hu.holyoil.view.IViewComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryListPanel extends JPanel implements IViewComponent {

    private PlayerStorage storage;
    private Settler settler;
    private JList<AbstractBaseResource> inventory;
    private JLabel tps;
    private DefaultListModel<AbstractBaseResource> model;
    private JButton craftRobot;
    private JButton craftTp;
    private JButton placeTp;
    private JButton fill;

    private void InitComponent() {
        setLayout(new GridBagLayout());
        settler = TurnController.GetInstance().GetSteppingSettler();
        storage = TurnController.GetInstance().GetSteppingSettler().GetStorage();

        model = new DefaultListModel<AbstractBaseResource>();
        storage.GetStoredMaterials().forEach(abr -> model.addElement(abr));
        inventory = new JList<>(model);

        inventory.setLayoutOrientation(JList.VERTICAL);
        inventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventory.setBackground(new Color(4,4,13));
        inventory.setForeground(Color.GREEN);

        JScrollPane scrollPane = new JScrollPane(inventory);
        scrollPane.setVerticalScrollBar(new JScrollBar());
        scrollPane.setBackground(new Color(4,4,13));
        scrollPane.setPreferredSize(new Dimension(360, 300));

        tps= new JLabel(String.valueOf(storage.GetTeleporterCount()));
        JLabel tpText = new JLabel("Number of TeleportGates: ");
        craftRobot = new JButton("Craft Robot");
        craftTp = new JButton("Craft Teleporter");
        placeTp = new JButton("Place Teleporter");
        fill = new JButton("Place Resource");

        JPanel panel1 = new JPanel();
        panel1.setOpaque(false);
        panel1.add(fill);
        panel1.add(craftRobot);

        JPanel panel2 = new JPanel();
        panel2.setOpaque(false);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        JPanel panel3 = new JPanel();
        panel3.setOpaque(false);
        panel3.add(tpText);
        panel3.add(tps);
        JPanel panel4 = new JPanel();
        panel4.setOpaque(false);
        panel4.add(craftTp);
        panel4.add(placeTp);
        panel2.add(panel3);
        panel2.add(panel4);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy=0; c.gridx=0;
        c.gridwidth=360;

        add(scrollPane, c);
        c.gridy=1;
        add(panel1, c);
        c.gridy=2;
        add(panel2, c);

        setOpaque(false);
        setVisible(true);
    }

    private void InitListeners() {
        craftRobot.addActionListener(e -> settler.CraftRobot());
        craftTp.addActionListener(e -> settler.CraftTeleportGate());
        placeTp.addActionListener(e -> settler.PlaceTeleporter());
        fill.addActionListener(e -> {
            if(!inventory.isSelectionEmpty())
                settler.PlaceResource(inventory.getSelectedValue());
        });
    }

    @Override
    public void UpdateComponent() {
        settler = TurnController.GetInstance().GetSteppingSettler();
        storage = TurnController.GetInstance().GetSteppingSettler().GetStorage();
        model.clear();
        storage.GetStoredMaterials().forEach(abr -> model.addElement(abr));
        tps.setText(String.valueOf(storage.GetTeleporterCount()));
        invalidate();
    }

    public InventoryListPanel() {
        super();
        InitComponent();
        InitListeners();
        setPreferredSize(new Dimension(360, 400));
        setOpaque(false);
        setBackground(new Color(4, 4, 13));
    }
}
