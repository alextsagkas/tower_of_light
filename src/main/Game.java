package main;

import characters.player.Race;
import characters.player.Warrior;
import interfaces.Restartable;

import javax.swing.*;
import java.awt.*;

public final class Game implements Restartable {
    private final JFrame window;
    public final GamePanel gamePanel;
    public final InfoPanel infoPanel;
    public final PlayerStats playerStats;
    public final GameLog gameLog;
    private static Race race;
    private static Warrior warrior;

    private Game() {
        // Game arena
        gamePanel = new GamePanel(this);

        // Information panel
        playerStats = new PlayerStats(gamePanel.player);
        gameLog = new GameLog();

        infoPanel = new InfoPanel();
        infoPanel.add(playerStats, BorderLayout.NORTH);
        infoPanel.add(gameLog, BorderLayout.SOUTH);

        // Whole window
        window = new JFrame();

        window.setTitle("Tower of Light");
        window.setResizable(false);
        window.setLayout(new BorderLayout());
        window.add(gamePanel, BorderLayout.CENTER);
        window.add(infoPanel, BorderLayout.EAST);
        window.pack();

        // Settings
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null); // display at the center of the screen
        window.setVisible(true);
    }

    public static void setRace(Race race) {
        Game.race = race;
    }

    public static Race getRace() {
        return Game.race;
    }

    public static void setWarrior(Warrior warrior) {
        Game.warrior = warrior;
    }

    public static Warrior getWarrior() {
        return Game.warrior;
    }

    private void start() {
        // Exit the game only through a dialog
        while (true) {
            // Start game
            gamePanel.startGame();

            // Dialogs
            if (gamePanel.isWin()) {
                Dialog winDialog = new Dialog(this);
                winDialog.showWinDialog(window);
            }
        }

    }

    public void restart() {
        gamePanel.restart();
        playerStats.restart();
        gameLog.restart();
    }

    public static void main(String[] args) {
        try {
            Game.setRace(Race.parseRace(args[0]));
            Game.setWarrior(Warrior.parseWarrior(args[1]));
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument provided: " + e.getMessage() + ".");
            System.err.println("Usage: java Game <race> <warrior>.");
            System.err.println("- <race>: Elf, Human, Orc, Tauren.");
            System.err.println("- <warrior>: Knight, Mage, Paladin.");
            System.exit(1);
        }

        Game game = new Game();

        game.start();
    }
}
