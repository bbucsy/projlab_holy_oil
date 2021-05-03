package hu.holyoil.view.panels;

import hu.holyoil.controller.TurnController;
import hu.holyoil.crewmate.Settler;
import hu.holyoil.repository.AsteroidRepository;
import hu.holyoil.repository.SettlerRepository;
import hu.holyoil.view.IViewComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

public class PlayerListPanel extends JPanel implements IViewComponent {

    private HashMap<Settler, PlayerInfoPanel> playerList;

    public PlayerListPanel() {
        super();
        this.InitComponent();
        setPreferredSize(new Dimension(360, 200));
        setBackground(new Color(65, 4, 13));

    }

    private void InitComponent() {
        playerList = new HashMap<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        for (Settler s : SettlerRepository.GetInstance().GetAll()) {
            PlayerInfoPanel pi = new PlayerInfoPanel();
            pi.setPlayerName(s.GetId());
            pi.setPlayerState(PlayerInfoPanel.PlayerState.Waiting);
            pi.setPlayerAsteroid(AsteroidRepository.GetInstance().GetAll().get(0).GetId());
            add(pi);
            playerList.put(s, pi);
        }
    }

    private void InitListeners() {
        //nothing to do here
    }

    @Override
    public void UpdateComponent() {
        playerList.forEach((s, i) -> {
            i.setPlayerName(s.GetId());
            i.setPlayerAsteroid(s.GetOnAsteroid().GetId());
            if (SettlerRepository.GetInstance().GetAll().contains(s)) {
                // settler alive
                if (TurnController.GetInstance().GetSteppingSettler() == s)
                    i.setPlayerState(PlayerInfoPanel.PlayerState.Active);
                else
                    i.setPlayerState(PlayerInfoPanel.PlayerState.Waiting);
            } else //settler dead
                i.setPlayerState(PlayerInfoPanel.PlayerState.Dead);
        });
    }
}
