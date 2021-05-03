package hu.holyoil.view.panels;

import hu.holyoil.controller.SunController;
import hu.holyoil.controller.TurnController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.resource.*;
import hu.holyoil.view.IViewComponent;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class EnvironmentPanel extends JPanel implements IViewComponent {
    /**
     * Jelenleg soron lévő játékos telepesét tárolja (könnyebb elérhetésért)
     */
    private Settler player;

    private JLabel sunstormCountLabel;

    private final Image asteroidImg = new ImageIcon("assets/plain_asteroid.png").getImage();
    private final Image coalImg = new ImageIcon("assets/coal.gif").getImage();
    private final Image waterImg = new ImageIcon("assets/water.gif").getImage();
    private final Image ironImg = new ImageIcon("assets/iron.gif").getImage();
    private final Image uraniumImg = new ImageIcon("assets/uranium.gif").getImage();

    private void InitComponent() {
        JLabel sunstormStaticLabel = new JLabel("Next sunstorm's imminent in: ");
        sunstormStaticLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));
        sunstormStaticLabel.setForeground(Color.white);

        sunstormCountLabel = new JLabel();
        sunstormCountLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));
        sunstormCountLabel.setForeground(Color.white);

        FlowLayout layout = new FlowLayout(FlowLayout.LEADING);
        setLayout(layout);
        add(sunstormStaticLabel);
        add(sunstormCountLabel);
    }

    private void InitListeners() {

    }

    boolean canDraw;

    private Image DefineImageFrom(AbstractBaseResource res) {
        Image image = null;
        if (res.IsSameType(new Uranium()))
            image = uraniumImg;
        else if (res.IsSameType(new Coal()))
            image = coalImg;
        else if (res.IsSameType(new Iron()))
            image = ironImg;
        else if (res.IsSameType(new Water()))
            image = waterImg;
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!canDraw)
            return;

        List<Asteroid> asteroids = player.GetOnAsteroid().GetNeighbours();
        for (int i = 0; i < asteroids.size(); ++i) {
            double phi = 2 * Math.PI * i / asteroids.size();
            int x = (int) (Math.cos(phi) * 280) + 330;
            int y = (int) (Math.sin(phi) * 220) + 260;
            g.drawImage(asteroidImg, x, y, 42, 42, this);

            AbstractBaseResource res = asteroids.get(i).GetResource();
            if (res == null)
                continue;
            g.drawImage(DefineImageFrom(res), x + 1, y + 1, 40, 40, this);
        }

        g.drawImage(asteroidImg, 330, 260, 42, 42, this);
        AbstractBaseResource res = player.GetOnAsteroid().GetResource();
        if (res == null)
            return;
        g.drawImage(DefineImageFrom(res), 331, 261, 40, 40, this);
    }

    @Override
    public void UpdateComponent() {
        player = TurnController.GetInstance().GetSteppingSettler();
        sunstormCountLabel.setText(SunController.GetInstance().GetTurnsUntilStorm() + " turn(s)");
    }

    public EnvironmentPanel() {
        super();
        InitComponent();
        InitListeners();
        setPreferredSize(new Dimension(720, 600));
        setOpaque(false);
        UpdateComponent();
        canDraw = true;
    }
}
