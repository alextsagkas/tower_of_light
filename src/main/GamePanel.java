package main;

import characters.Player;
import map.DiscreteMap;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    // Game state
    private int gameLevel = 1;

    // Instances of other classes
    public TileManager tileManager = new TileManager(this);

    public KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this);

    public CollisionChecker collisionChecker = new CollisionChecker(this);

    // Getters
    public int getGameLevel() {
        return gameLevel;
    }

    // Setters
    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }

    public GamePanel() {
        final int screenHeight = DiscreteMap.tileSize * DiscreteMap.maxScreenRow;
        final int screenWidth = DiscreteMap.tileSize * DiscreteMap.maxScreenCol;

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.WHITE);

        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void startGame() {
        while (gameLevel <= 6) {
            if (keyHandler.advanceTime) {
                // Update information about the game
                update();
                // Draw the screen with updated information
                repaint();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        player.update();
        tileManager.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Draw game entities
        tileManager.draw(g2d);
        player.draw(g2d);
    }
}
