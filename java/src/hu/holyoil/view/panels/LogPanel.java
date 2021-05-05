package hu.holyoil.view.panels;

import hu.holyoil.commandhandler.Logger;
import hu.holyoil.controller.GameController;
import hu.holyoil.controller.TurnController;
import hu.holyoil.view.IViewComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LogPanel extends JPanel implements IViewComponent {

    private String logString;

    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    private JScrollPane jScrollPane;
    private JButton skipButton;
    private JButton giveUpButton;
    private JTextArea jTextArea;

    /* see: https://stackoverflow.com/questions/1760654/java-printstream-to-string/1760668 */
    private void InitComponent() {
        logString = "";
        try {
            PrintStream printStream = new PrintStream(byteArrayOutputStream, true, "utf-8");
            Logger.SetPrintStream(printStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        skipButton = new JButton("Skip turn", null);
        giveUpButton = new JButton("Give Up Game", null);
        skipButton.setPreferredSize(new Dimension(100, 50));
        giveUpButton.setPreferredSize(new Dimension(140, 50));

        jTextArea = new JTextArea(35, 50);
        jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setPreferredSize(new Dimension(720, 110));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jTextArea.setVisible(true);
        jTextArea.setEditable(false);
        jTextArea.setBackground(new Color(4, 4, 13));
        jTextArea.setForeground(Color.GREEN);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        setLayout(flowLayout);

        add(jScrollPane);
        add(Box.createRigidArea(new Dimension(20, 50)));
        add(skipButton);
        add(Box.createRigidArea(new Dimension(40, 50)));
        add(giveUpButton);
    }

    private void InitListeners() {
        skipButton.addActionListener(e -> TurnController.GetInstance().ReactToActionMade(TurnController.GetInstance().GetSteppingSettler()));
        giveUpButton.addActionListener(e -> GameController.GetInstance().CloseGame());
    }

    @Override
    public void UpdateComponent() {
        try {
            logString = byteArrayOutputStream.toString("utf-8");
        } catch (Exception ignore) {

        }

        jTextArea.setText(logString);
    }

    public LogPanel() {
        super();
        setPreferredSize(new Dimension(1080, 120));
        setOpaque(false);
        InitComponent();
        InitListeners();
    }
}
