package main;

import characters.Player;
import main.colors.UIColors;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    // Game state
    private int gameLevel = 1;
    public final int maxGameLevel = 6;
    private boolean win = false;

    // Instances of other classes
    public TileManager tileManager = new TileManager(this);

    public KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this);

    public CollisionChecker collisionChecker = new CollisionChecker(this);

    // Getters
    public int getGameLevel() {
        return gameLevel;
    }

    public boolean isWin() {
        return win;
    }

    // Setters
    public void setWin(boolean win) {
        this.win = win;
    }

    public GamePanel() {
        final int screenHeight = DiscreteMap.tileSize * DiscreteMap.maxScreenRow;
        final int screenWidth = DiscreteMap.tileSize * DiscreteMap.maxScreenCol;

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(UIColors.backgroundColor);

        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void startGame() {
        while (!win) {
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

    private void reset_components() {
        player.reset();
        tileManager.reset();
    }

    private void update_components() {
        player.update();
        tileManager.update();
    }

    private void update() {
        update_components();

        DiscreteMapPosition playerPosition = player.getPosition();

        if (playerPosition.equals(DiscreteMap.northEast) && tileManager.getSpellTilesSize() == tileManager.maxSpellTiles) {
            if (getGameLevel() < maxGameLevel) {
                gameLevel++;
                reset_components();
            } else {
                setWin(true);
            }
        }
    }

    public void restart() {
        gameLevel = 1;
        win = false;
        reset_components();
        update_components();
        keyHandler.advanceTime = true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Draw game entities
        tileManager.draw(g2d);
        player.draw(g2d);
    }
}
