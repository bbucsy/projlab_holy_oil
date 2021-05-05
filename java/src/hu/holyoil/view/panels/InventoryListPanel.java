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
    //private JTable invTable;
    private JList<AbstractBaseResource> inventory;
    private JLabel tps;
    private DefaultListModel<AbstractBaseResource> model;

    private void InitComponent() {
      /*  GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);*/
        settler = TurnController.GetInstance().GetSteppingSettler();
        storage = TurnController.GetInstance().GetSteppingSettler().GetStorage();
        JLabel invTitle = new JLabel("Inventory");
        invTitle.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));
        invTitle.setForeground(Color.WHITE);

        model = new DefaultListModel<AbstractBaseResource>();
        storage.GetStoredMaterials().forEach(abr -> model.addElement(abr));
        inventory = new JList<>(model);

        inventory.setLayoutOrientation(JList.VERTICAL);
        inventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventory.setBackground(new Color(30,60,90));
        inventory.setForeground(new Color(220, 150, 180)); //this colour is designed specifically to make trisztán angry. Handle with care.

        JScrollPane scrollPane = new JScrollPane(inventory);
        scrollPane.setVerticalScrollBar(new JScrollBar());
        scrollPane.setBackground(new Color(4,4,13));
        scrollPane.setPreferredSize(new Dimension(150, 200));

        tps= new JLabel("# of teleporters:\n" + storage.GetTeleporterCount());

        JButton craftRobot = new JButton("Craft Robot");
        craftRobot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settler.CraftRobot();
            }
        });
        JButton craftTp = new JButton("Craft Teleporter");
        craftTp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settler.CraftTeleportGate();
            }
        });
        JButton placeTp = new JButton("Place Teleporter");
        placeTp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settler.PlaceTeleporter();
            }
        });
        JButton fill = new JButton("Fill");
        fill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inventory.isSelectionEmpty())
                    settler.PlaceResource(inventory.getSelectedValue());
            }
        });

        add(invTitle);
        add(scrollPane);
        add(craftRobot);
        add(craftTp);
        add(placeTp);
        add(fill);
        add(tps);

        setVisible(true);
    }

    private void InitListeners() {

    }

    @Override
    public void UpdateComponent() {
        settler = TurnController.GetInstance().GetSteppingSettler();
        storage = TurnController.GetInstance().GetSteppingSettler().GetStorage();
        model.clear();
        storage.GetStoredMaterials().forEach(abr -> model.addElement(abr));
        tps.setText("# of teleporters:\n" + storage.GetTeleporterCount());
        invalidate();
    }

    public InventoryListPanel() {
        super();
        InitComponent();
        setPreferredSize(new Dimension(360, 400));
        setOpaque(false);
        setBackground(new Color(4, 4, 13));
        UpdateComponent();
    }
}
