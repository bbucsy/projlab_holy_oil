package hu.holyoil.view.panels;

import hu.holyoil.controller.TurnController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.*;
import hu.holyoil.storage.PlayerStorage;
import hu.holyoil.view.IViewComponent;

import javax.swing.*;
import java.awt.*;

/**
 * A soron lévő settler tárolóegységét mutatja, és extra műveleteket kínál.
 * <p>
 *     Ezek a műveletek:<ul>
 *         <li>Robot gyártása</li>
 *         <li>Teleporter gyártása</li>
 *         <li>Teleporter elhelyezése</li>
 *         <li>Kiválasztott nyersanyag visszatöltése üres aszteroidába</li>
 *     </ul>
 * </p>
 */
public class InventoryListPanel extends JPanel implements IViewComponent {
    /**
     * A soron lévő játékos tárhelye
     */
    private PlayerStorage storage;
    /**
     * A soron lévő játékos
     */
    private Settler settler;
    /**
     * A megjeleníti a nyersanyagok listáját
     */
    private JList<AbstractBaseResource> inventory;
    /**
     * A megjelenítendő lista modelje
     */
    private DefaultListModel<AbstractBaseResource> model;
    /**
     * A teleporter számát megjelenítő felirat
     */
    private JLabel tps;
    /**
     * Egy robot gyártására szolgáló gomb
     */
    private JButton craftRobot;
    /**
     * Egy teleporter pár gyártására szolgáló gomb
     */
    private JButton craftTp;
    /**
     * Egy teleportert helyez el a jelen aszteroidán
     */
    private JButton placeTp;
    /**
     * Lerakja a kiválasztott nyersanyagot a jelen aszteroidára
     */
    private JButton fill;

    /**
     * Inicializálja a panelt
     */
    private void InitComponent() {
        setLayout(new GridBagLayout());
        settler = TurnController.GetInstance().GetSteppingSettler();
        storage = TurnController.GetInstance().GetSteppingSettler().GetStorage();

        //betölti a nyersanyagok listáját
        model = new DefaultListModel<>();
        storage.GetStoredMaterials().forEach(abr -> model.addElement(abr));
        inventory = new JList<>(model);

        inventory.setLayoutOrientation(JList.VERTICAL);
        inventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventory.setBackground(new Color(4,4,13)); //lista háttere (nem lehet átlátszó)
        inventory.setCellRenderer(new InventoryCellRenderer());

        JScrollPane scrollPane = new JScrollPane(inventory);
        scrollPane.setVerticalScrollBar(new JScrollBar());
        scrollPane.setBackground(new Color(4,4,13));
        scrollPane.setPreferredSize(new Dimension(320, 280));

        JLabel tpText = new JLabel("Number of TeleportGates: ");
        tps= new JLabel(String.valueOf(storage.GetTeleporterCount()));
        craftRobot = new JButton("Craft Robot");
        craftTp = new JButton("Craft Teleporter");
        placeTp = new JButton("Place Teleporter");
        fill = new JButton("Place Resource");
        //első két gomb
        JPanel panel1 = new JPanel();
        panel1.setOpaque(false);
        panel1.add(fill);
        fill.setEnabled(false);
        panel1.add(placeTp);
        //teleporter szöveg és alsó két gomb
        JPanel panel2 = new JPanel();
        panel2.setOpaque(false);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        JPanel panel3 = new JPanel();
        panel3.setOpaque(false);
        panel3.add(tpText);
        panel3.add(tps);
        JPanel panel4 = new JPanel();
        panel4.setOpaque(false);
        panel4.add(craftRobot);
        panel4.add(craftTp);
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

    /**
     * Listenereket ad a gombokhoz
     */
    private void InitListeners() {
        craftRobot.addActionListener(e -> settler.CraftRobot());
        craftTp.addActionListener(e -> settler.CraftTeleportGate());
        placeTp.addActionListener(e -> settler.PlaceTeleporter());
        fill.addActionListener(e -> {
            if(!inventory.isSelectionEmpty()) settler.PlaceResource(inventory.getSelectedValue());
        });
        inventory.addListSelectionListener(e -> fill.setEnabled(!(inventory.isSelectionEmpty())
                && settler.GetOnAsteroid().GetResource()==null
                && settler.GetOnAsteroid().GetLayerCount()==0)
        );
    }

    /**
     * Újratölti a körök végén a tárhelyeket.
     */
    @Override
    public void UpdateComponent() {
        Asteroid current= settler.GetOnAsteroid();
        InitListeners();
        placeTp.setEnabled(storage.GetTeleporterCount()!=0 && current.GetTeleporter()==null);
        settler = TurnController.GetInstance().GetSteppingSettler();
        storage = TurnController.GetInstance().GetSteppingSettler().GetStorage();
        model.clear();
        storage.GetStoredMaterials().forEach(abr -> model.addElement(abr));
        tps.setText(String.valueOf(storage.GetTeleporterCount()));
        invalidate();
    }

    /**
     * Konstruktor
     */
    public InventoryListPanel() {
        super();
        InitComponent();
        InitListeners();
        setPreferredSize(new Dimension(360, 400));
        setOpaque(false);
        setBackground(new Color(4, 4, 13));
    }

    private class InventoryCellRenderer extends DefaultListCellRenderer {
        private final Color textSelectionColor = Color.DARK_GRAY;
        private final Color backgroundSelectionColor = Color.GREEN;

        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean selected,
                boolean expanded) {

            AbstractBaseResource res = (AbstractBaseResource)value;
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(res.GetImage()));
            label.setText(res.toString());

            if (selected) {
                label.setOpaque(true);
                label.setBackground(backgroundSelectionColor);
                label.setForeground(textSelectionColor);
            }
            else {
                label.setOpaque(false);
                label.setForeground(backgroundSelectionColor);
            }

            return label;
        }
    }

}
