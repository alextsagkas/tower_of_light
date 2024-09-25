package main;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public final class Dialog {
    private final Game game;

    public Dialog(Game game) {
        this.game = game;
    }

    public void showWinDialog(
            JFrame frame,
            String title,
            String message
    ) {
        // Create the dialog
        JDialog winDialog = new JDialog(frame, title, true);
        winDialog.setLayout(new BorderLayout());

        // Message label
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        winDialog.add(messageLabel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = getButtonPanel(winDialog);
        winDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Dialog properties
        winDialog.setSize(400, 200);
        winDialog.setLocationRelativeTo(game.gamePanel); // Center the dialog over the gamePanel
        winDialog.setVisible(true);
    }

    private @NotNull JPanel getButtonPanel(JDialog winDialog) {
        JPanel buttonPanel = new JPanel();

        // Buttons
        JButton resetButton = new JButton("Restart");
        JButton closeButton = new JButton("Close");

        // Button actions
        resetButton.addActionListener(_ -> {
            game.restart();
            winDialog.dispose();
        });
        closeButton.addActionListener(_ -> System.exit(0));

        buttonPanel.add(resetButton);
        buttonPanel.add(closeButton);

        return buttonPanel;
    }
}
