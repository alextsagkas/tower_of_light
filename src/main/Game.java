package main;

import javax.swing.*;

public final class Game {
    private final JFrame window;
    public final GamePanel gamePanel;

    private Game() {
        window = new JFrame();

        window.setTitle("Tower of Light");
        window.setResizable(false);

        // Add components to it
        gamePanel = new GamePanel();
        window.add(gamePanel);

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
