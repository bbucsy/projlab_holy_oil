package hu.holyoil.view.panels;

import javax.swing.*;
import java.awt.*;


public class PlayerInfoPanel extends JPanel {

    private static final ImageIcon imgDead = new ImageIcon("assets/dead.gif");
    private static final Color normalColor = new Color(4, 4, 13);
    private static final Color activeColor = new Color(44, 123, 23);
    private JLabel playerName;
    private JLabel playerAsteroid;
    private JLabel playerState;
    public PlayerInfoPanel() {
        super();
        initComponents();
        setPlayerState(PlayerState.Dead);
        setPreferredSize(new Dimension(360, 50));
        setMaximumSize(new Dimension(360, 50));
        setBackground(normalColor);
    }

    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));
        playerName = new JLabel();
        playerAsteroid = new JLabel();
        playerState = new JLabel();


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
                playerState.setIcon(null);
                setBackground(activeColor);
                break;
            case Waiting:
                playerState.setIcon(null);
                setBackground(normalColor);
                break;
            case Dead:
                playerState.setIcon(imgDead);
                setBackground(normalColor);
                break;
        }
    }


    public enum PlayerState {
        Active,
        Dead,
        Waiting
    }


}
