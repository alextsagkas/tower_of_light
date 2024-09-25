package main;

import interfaces.LogObserver;
import interfaces.Restartable;
import map.DiscreteMap;
import ui.Colors;

import javax.swing.*;
import java.awt.*;

public final class GameLog extends JPanel implements LogObserver, Restartable {
    private final JLabel titleLabel;
    private final JTextArea textArea;
    private final JScrollPane scrollPane;

    public GameLog() {
        super(new BorderLayout());

        // Title
        titleLabel = new JLabel();
        customizeTitleLabel();

        // Transcript
        textArea = new JTextArea();
        customizeTextArea();
        scrollPane = new JScrollPane();
        customizeScrollPane();

        // Add components to JPanel
        customizePanel();
    }

    private void customizeTitleLabel() {
        String title = "<html>" + "<h3>========= Transcript =========</h3>" + "<br>";
        titleLabel.setText(title);
        titleLabel.setFont(InfoPanel.font);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void customizeTextArea() {
        textArea.setFocusable(false);
        textArea.setRows(DiscreteMap.maxScreenRow / 4);
        textArea.setColumns(DiscreteMap.maxScreenCol);
        textArea.setEditable(false);
        textArea.setBackground(Colors.infoPanelBackgroundColor);
        textArea.setForeground(Colors.infoPanelForegroundColor);
        textArea.setFont(InfoPanel.font);
        textArea.setBorder(BorderFactory.createEmptyBorder(0, InfoPanel.padding, InfoPanel.padding, 0));
    }

    private void customizeScrollPane() {
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null);
        scrollPane.setViewportView(textArea);
    }

    private void customizePanel() {
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.SOUTH);
        setBackground(Colors.infoPanelBackgroundColor);
        setForeground(Colors.infoPanelForegroundColor);
    }

    private void appendText(String info) {
        textArea.append(info);
    }

    private void moveScrollBarBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar jsb = scrollPane.getVerticalScrollBar();
            jsb.setValue(jsb.getMaximum());
        });
    }

    public void updateLog(String log) {
        appendText(log + "\n");
        moveScrollBarBottom();
    }

    public void restart() {
        textArea.setText("Player stats have been refreshed." + "\n");
    }
}
