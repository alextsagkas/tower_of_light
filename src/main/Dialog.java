package main;

import javax.swing.*;
import java.awt.*;

public final class Dialog {
    private final GamePanel gamePanel;

    public Dialog(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void showWinDialog(JFrame frame) {
        // Create the dialog
        JDialog winDialog = new JDialog(frame, "You Win!", true);
        winDialog.setLayout(new BorderLayout());

        // Message label
        JLabel messageLabel = new JLabel(
                "You converted all Tower levers to Light.",
                SwingConstants.CENTER
        );
        winDialog.add(messageLabel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = getButtonPanel(winDialog);
        winDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Dialog properties
        winDialog.setSize(400, 200);
        winDialog.setLocationRelativeTo(gamePanel); // Center the dialog over the gamePanel
        winDialog.setVisible(true);
    }

    private JPanel getButtonPanel(JDialog winDialog) {
        JPanel buttonPanel = new JPanel();

        // Buttons
        JButton resetButton = new JButton("Restart");
        JButton closeButton = new JButton("Close");

        // Button actions
        resetButton.addActionListener(_ -> {
            gamePanel.restart();
            winDialog.dispose();
        });
        closeButton.addActionListener(_ -> System.exit(0));

        buttonPanel.add(resetButton);
        buttonPanel.add(closeButton);

        return buttonPanel;
    }
}
