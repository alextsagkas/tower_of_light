package main;

import interfaces.LogObserver;
import map.DiscreteMap;
import ui.Colors;

import javax.swing.*;
import java.awt.*;

public class GameLog extends JPanel implements LogObserver {
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
        String title = "<html>" + "<h2>~~~~~~~~ transcript ~~~~~~~~</h2>" + "<br>";
        titleLabel.setText(title);
        titleLabel.setFont(InfoPanel.font);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void customizeTextArea() {
        textArea.setRows(DiscreteMap.maxScreenRow / 5);
        textArea.setColumns(DiscreteMap.maxScreenCol);
        textArea.setEditable(false);
        textArea.setBackground(Colors.infoPanelBackgroundColor);
        textArea.setForeground(Colors.infoPanelForegroundColor);
        textArea.setFont(InfoPanel.font);
        textArea.setBorder(BorderFactory.createEmptyBorder(0, InfoPanel.padding, InfoPanel.padding, 0));
    }

    private void customizeScrollPane() {
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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


    public void update(String log) {
        appendText(log + "\n");
        moveScrollBarBottom();
    }
}
