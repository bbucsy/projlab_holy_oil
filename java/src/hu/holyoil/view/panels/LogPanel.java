package hu.holyoil.view.panels;

import hu.holyoil.commandhandler.Logger;
import hu.holyoil.controller.TurnController;
import hu.holyoil.view.IViewComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LogPanel extends JPanel implements IViewComponent {

    private String logString;

    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    private JScrollPane jScrollPane;
    private JButton jButton;
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

        jButton = new JButton("Skip turn", null);
        jTextArea = new JTextArea(35, 50);
        jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setPreferredSize(new Dimension(600, 100));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jTextArea.setVisible(true);
        jTextArea.setEditable(false);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        setLayout(flowLayout);

        add(jScrollPane);
        add(jButton);

    }

    private void InitListeners() {

        jButton.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        TurnController.GetInstance().ReactToActionMade(
                                TurnController.GetInstance().GetSteppingSettler()
                        );
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        // do nothing
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // do nothing
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // do nothing
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // do nothing
                    }
                }
        );

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
        setBackground(new Color(4, 4, 13));
        InitComponent();
        InitListeners();
    }
}
