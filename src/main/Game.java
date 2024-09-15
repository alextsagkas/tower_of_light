package main;

import javax.swing.*;
import java.awt.*;

public final class Game {
    private final JFrame window;
    public final GamePanel gamePanel;
    public final InfoPanel infoPanel;
    public final PlayerStatus playerStatus;
    public final GameLog gameLog;

    private Game() {
        window = new JFrame();

        window.setTitle("Tower of Light");
        window.setResizable(false);
        window.setLayout(new BorderLayout());

        gamePanel = new GamePanel(this);

        infoPanel = new InfoPanel(new BorderLayout());

        playerStatus = new PlayerStatus(gamePanel.player);
        gameLog = new GameLog();

        infoPanel.add(playerStatus, BorderLayout.NORTH);
        infoPanel.add(gameLog, BorderLayout.SOUTH);

        window.add(gamePanel, BorderLayout.CENTER);
        window.add(infoPanel, BorderLayout.EAST);

        window.pack();

        // Settings
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null); // display at the center of the screen
        window.setVisible(true);
    }

    private void start() {
        // Exit the game only through a dialog
        while (true) {
            // Start game
            gamePanel.startGame();

            // Dialogs
            if (gamePanel.isWin()) {
                Dialog winDialog = new Dialog(gamePanel);
                winDialog.showWinDialog(window);
            }
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
