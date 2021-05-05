package hu.holyoil.view.panels;

import javax.swing.*;
import java.awt.*;


public class PlayerInfoPanel extends JPanel {

    private static final ImageIcon imgDead = new ImageIcon("assets/dead.gif");
    private static final ImageIcon imgSettler = new ImageIcon("assets/settler.gif");
    private JLabel playerName;
    private JLabel playerAsteroid;
    private JLabel playerState;
    public PlayerInfoPanel() {
        super();
        initComponents();
        setPlayerState(PlayerState.Dead);
        setPreferredSize(new Dimension(360, 45));
        setMaximumSize(new Dimension(360, 45));
        setOpaque(false);
    }

    private void initComponents() {
        FlowLayout flow = new FlowLayout(FlowLayout.LEFT, 30, 5);
        setLayout(flow);
        playerName = new JLabel();
        playerName.setForeground(Color.WHITE);
        playerName.setVerticalAlignment(JLabel.CENTER);
        playerName.setHorizontalAlignment(JLabel.RIGHT);
        playerName.setPreferredSize(new Dimension(80, 45));
        playerAsteroid = new JLabel();
        playerAsteroid.setForeground(Color.WHITE);
        playerAsteroid.setVerticalAlignment(JLabel.CENTER);
        playerAsteroid.setPreferredSize(new Dimension(80, 45));
        playerState = new JLabel();
        playerState.setPreferredSize(new Dimension(50, 45));

        add(playerAsteroid);
        add(playerName);
        add(playerState);
    }

    public void setPlayerName(String playerName) {
        this.playerName.setText(playerName);
    }

    public void setPlayerAsteroid(String playerAsteroid) {
        this.playerAsteroid.setText(playerAsteroid);
    }

    public void setPlayerState(PlayerState state) {
        switch (state) {
            case Active:
                playerState.setIcon(imgSettler);
                break;
            case Waiting:
                playerState.setIcon(null);
                setOpaque(false);
                break;
            case Dead:
                playerState.setIcon(imgDead);
                setOpaque(false);
                break;
        }
    }


    public enum PlayerState {
        Active,
        Dead,
        Waiting
    }


}
