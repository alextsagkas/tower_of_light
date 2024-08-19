package main;

import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    // Screen settings
    private final int tileSize = 16;
    private final int maxScreenCol = 52;
    private final int maxScreenRow = maxScreenCol;

    // Game state
    private int gameLevel = 1;

    // Instances of other classes
    TileManager tileManager = new TileManager(this);

    // Getters
    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getGameLevel() {
        return gameLevel;
    }

    // Setters
    public void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }

    public GamePanel() {
        final int screenHeight = tileSize * maxScreenRow;
        final int screenWidth = tileSize * maxScreenCol;

        setPreferredSize(new Dimension(screenWidth, screenHeight));

        setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        tileManager.draw(g2d);
    }
}
