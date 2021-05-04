package hu.holyoil.view.panels;

import hu.holyoil.controller.SunController;
import hu.holyoil.controller.TurnController;
import hu.holyoil.crewmate.AbstractSpaceship;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.neighbour.TeleportGate;
import hu.holyoil.resource.*;
import hu.holyoil.view.IViewComponent;
import hu.holyoil.view.popupmenus.AbstractPopupMenu;
import hu.holyoil.view.popupmenus.AsteroidPopupMenu;
import hu.holyoil.view.popupmenus.SettlerActionPopupMenu;
import hu.holyoil.view.popupmenus.TeleportGatePopupMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class EnvironmentPanel extends JPanel implements IViewComponent {
    /**
     * Jelenleg soron lévő játékos telepesét tárolja (könnyebb elérhetésért)
     */
    private Settler player;

    /**
     * Körönként változó felirat, amely mutatja a napvihar visszaszámlálót.
     */
    private JLabel sunstormCountLabel;

    // Képek
    private final Image asteroidImg = new ImageIcon("assets/plain_asteroid.png").getImage();
    private final Image coalImg = new ImageIcon("assets/coal.gif").getImage();
    private final Image waterImg = new ImageIcon("assets/water.gif").getImage();
    private final Image ironImg = new ImageIcon("assets/iron.gif").getImage();
    private final Image uraniumImg = new ImageIcon("assets/uranium.gif").getImage();
    private final Image teleportImg = new ImageIcon("assets/teleporter.gif").getImage();

    /**
     * A szomszédos aszteroidák és azok hitboxának bal felső sarkát tároló map.
     */
    private final Map<Asteroid, Point> asteroidPointMap = new HashMap<>();

    private final Map<Rectangle, Image> imageMap = new HashMap<>();

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

    /**
     * Megvizsgálja, hogy a hitpoint benne van-e a dobozban, amit megadunk
     * @param hit érkezés helye
     * @param box doboz bal felső pontja
     * @param width doboz szélessége
     * @param height doboz magassága
     * @return benne van-e a dobozban
     */
    private boolean HitInBox(Point hit, Point box, int width, int height) {
        return hit.x >= box.x && hit.x <= box.x + width && hit.y >= box.y && hit.y <= box.y + height;
    }

    private void InitListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Point hit = e.getPoint();

                // foreach related transaction magic
                final Asteroid[] hitAsteroid = {null};
                asteroidPointMap.forEach((asteroid, point) -> {
                    if (HitInBox(hit, point, 44, 44))
                        hitAsteroid[0] = asteroid;
                });

                TeleportGate teleportGate = player.GetOnAsteroid().GetTeleporter();
                AbstractPopupMenu popupMenu = null;

                // checking click on neighbour asteroid, teleportgate or central asteroid
                if (hitAsteroid[0] != null)
                    popupMenu = new AsteroidPopupMenu(hitAsteroid[0], e);
                else if (HitInBox(hit, new Point(230, 210), 60, 60) && teleportGate != null)
                    popupMenu = new TeleportGatePopupMenu(teleportGate, e);
                else if (HitInBox(hit, new Point(330, 280), 64, 64))
                    popupMenu = new SettlerActionPopupMenu(player, e);

                if (popupMenu != null)
                    popupMenu.Show(e);
            }
        });
    }

    // Segédobjektumok a kép megállapítására
    // Megszűnéskor érdemes a repóból eltüntetni őket.
    private Uranium exampleUranium;
    private Iron exampleIron;
    private Coal exampleCoal;
    private Water exampleWater;

    /**
     * Segít megállapítani, melyik képre van szüksége a nyersanyagok képei közül.
     * @param res nyersanyag objektum
     * @return nyersanyag képe
     */
    private Image DefineImageFrom(AbstractBaseResource res) {
        Image image = null;
        if (res.IsSameType(exampleUranium))
            image = uraniumImg;
        else if (res.IsSameType(exampleCoal))
            image = coalImg;
        else if (res.IsSameType(exampleIron))
            image = ironImg;
        else if (res.IsSameType(exampleWater))
            image = waterImg;
        return image;
    }

    /**
     * Segít megállapítani, melyik képre van szüksége az játékban lévő entitások képei közül.
     * @param spaceship entitás
     * @return entitás képe
     */
    private Image DefineImageFrom(AbstractSpaceship spaceship) {
        return spaceship.GetImage();
    }

    /**
     * Betárolja az imageMap-be a rajzolandó aszteroidát körülvevő elemeket
     * @param asteroid amit körülveszik a dolgok
     * @param asteroidPoint az aszteroida kezdőpontja
     */
    private void AsteroidSurroundingToImageMap(Asteroid asteroid, Point asteroidPoint) {
        List<AbstractSpaceship> spaceships = asteroid.GetSpaceships();
        double phi = -0.5 * Math.PI;
        double deltaPhi = 2 * Math.PI / (double)spaceships.size();
        int x = (int) (Math.cos(phi) * 32) + asteroidPoint.x + 12;
        int y = (int) (Math.sin(phi) * 32) + asteroidPoint.y + 12;

        // if there's a teleporter, it gets drawn first
        if (asteroid.GetTeleporter() != null) {
            imageMap.put(new Rectangle(x, y, 20, 20), teleportImg);
            deltaPhi = 2 * Math.PI / (double)(spaceships.size() + 1);
            phi += deltaPhi;
        }

        for (AbstractSpaceship spaceship : spaceships) {
            x = (int) (Math.cos(phi) * 32) + asteroidPoint.x + 12;
            y = (int) (Math.sin(phi) * 32) + asteroidPoint.y + 12;
            imageMap.put(new Rectangle(x, y, 20, 20), DefineImageFrom(spaceship));
            phi += deltaPhi;
        }
    }

    /**
     * Betárolja a központi aszteroidát az imageMapbe
     */
    private void CentralAsteroidToImageMap() {
        // register central asteroid as drawable image
        imageMap.put(new Rectangle(330, 280, 64, 64), asteroidImg);

        // register central asteroid's resource as drawable image
        AbstractBaseResource res = player.GetOnAsteroid().GetResource();
        if (res != null)
            imageMap.put(new Rectangle(334, 284, 56, 56), DefineImageFrom(res));

        // register central asteroid's spaceships as drawable image
        List<AbstractSpaceship> spaceships = player.GetOnAsteroid().GetSpaceships();
        double phi = -0.5 * Math.PI;
        double deltaPhi = 2 * Math.PI / (double)spaceships.size();
        for (AbstractSpaceship spaceship : spaceships) {
            int x = (int) (Math.cos(phi) * 50) + 330 + 12;
            int y = (int) (Math.sin(phi) * 50) + 280 + 12;
            imageMap.put(new Rectangle(x, y, 40, 40), DefineImageFrom(spaceship));
            phi += deltaPhi;
        }
    }

    /**
     * Betárolja a központi aszteroida teleportkapuját az imageMapbe
     */
    private void TeleportGateToImageMap() {
        if (player.GetOnAsteroid().GetTeleporter() != null)
            imageMap.put(new Rectangle(230, 210, 60, 60), teleportImg);
    }

    /**
     * Betárolja a szomszédos aszteroidák hitboxát a kattintható aszteroidák mapjébe, majd
     * ezen aszteroidákat, nyersanyagukat és körülvevő entitásait az imageMapbe
     */
    private void NeighbourAsteroidsToImageMap() {
        List<Asteroid> asteroids = player.GetOnAsteroid().GetNeighbours();
        double phi = 0;
        double deltaPhi = 2 * Math.PI / asteroids.size();
        for (Asteroid asteroid : asteroids) {
            int x = (int) (Math.cos(phi) * 280) + 330;
            int y = (int) (Math.sin(phi) * 220) + 280;

            // register neighbour asteroid as drawable image AND clickable element
            asteroidPointMap.put(asteroid, new Point(x, y));
            imageMap.put(new Rectangle(x, y, 44, 44), asteroidImg);

            // register neighbour asteroid's resource as drawable image
            AbstractBaseResource res = asteroid.GetResource();
            if (res != null)
                imageMap.put(new Rectangle(x + 2, y + 2, 40, 40), DefineImageFrom(res));

            // register neighbour asteroid's spaceships (and teleporter if available)
            AsteroidSurroundingToImageMap(asteroid, new Point(x, y));

            phi += deltaPhi;
        }
    }

    /**
     * Képernyő invalidációja során lefutó metódus, amely override-ja szükséges a grafikus elemek kirajzolására.
     * @param g grafikus objektum
     */
    @Override
    protected void paintComponent(Graphics g) {
        imageMap.forEach((rectangle, image) -> g.drawImage(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height, this));
    }

    @Override
    public void UpdateComponent() {
        // clearing up data
        asteroidPointMap.clear();
        imageMap.clear();

        // get data from model
        player = TurnController.GetInstance().GetSteppingSettler();
        sunstormCountLabel.setText(SunController.GetInstance().GetTurnsUntilStorm() + " turn(s)");

        // set up example objects for resource comparison and image definition
        exampleUranium = new Uranium();
        exampleIron = new Iron();
        exampleCoal = new Coal();
        exampleWater = new Water();

        // register drawable items to imageMap
        NeighbourAsteroidsToImageMap();
        CentralAsteroidToImageMap();
        TeleportGateToImageMap();

        // update click listener on every player-change
        InitListeners();

        // get rid of example objects
        exampleCoal.ReactToGettingDestroyed();
        exampleIron.ReactToGettingDestroyed();
        exampleUranium.ReactToGettingDestroyed();
        exampleWater.ReactToGettingDestroyed();

        invalidate();
    }

    public EnvironmentPanel() {
        super();
        InitComponent();
        setPreferredSize(new Dimension(720, 600));
        setOpaque(false);
    }
}
