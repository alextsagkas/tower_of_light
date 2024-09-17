package main;

import ui.Colors;

import javax.swing.*;
import java.awt.*;

public class PlayerStats extends JPanel {
    private final JLabel titleLabel;
    private final JLabel statsLabel;

    public PlayerStats() {
        super(new BorderLayout());

        titleLabel = new JLabel();
        customizeTitleLabel();

        statsLabel = new JLabel();
        customizeStatusLabel();

        customizePanel();
    }

    private void customizeTitleLabel() {
        String title = "<html>" +
                "<h2>~~~~~~~ player stats ~~~~~~~</h2>";
        titleLabel.setText(title);
        titleLabel.setFont(InfoPanel.font);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void customizeStatusLabel() {
        String state = "<html>" +
                "<p>Hello, this is a random text.</p>";

        statsLabel.setText(state);
        statsLabel.setFont(InfoPanel.font);
    }

    private void customizePanel() {
        add(titleLabel, BorderLayout.NORTH);
        add(statsLabel, BorderLayout.SOUTH);
        setBackground(Colors.infoPanelBackgroundColor);
        setForeground(Colors.infoPanelForegroundColor);
        setBorder(BorderFactory.createEmptyBorder(0, InfoPanel.padding, InfoPanel.padding, 0));
    }
}
