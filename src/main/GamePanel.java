package main;

import characters.CollisionChecker;
import characters.Player;
import interfaces.Resettable;
import interfaces.Updatable;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import map.tiles.TileManager;
import ui.Colors;

import javax.swing.*;
import java.awt.*;

public final class GamePanel extends JPanel implements Updatable, Resettable {
    private int gameLevel;
    public final int maxGameLevel;
    private boolean win;

    public TileManager tileManager;
    public KeyHandler keyHandler;
    public Player player;
    public CollisionChecker collisionChecker;

    public GamePanel() {
        // Initialize attributes
        gameLevel = 1;
        maxGameLevel = 6;
        win = false;

        tileManager = new TileManager(this);
        keyHandler = new KeyHandler();
        player = new Player(this);
        collisionChecker = new CollisionChecker(this);

        // JPanel Settings
        final int screenHeight = DiscreteMap.tileSize * DiscreteMap.maxScreenRow;
        final int screenWidth = DiscreteMap.tileSize * DiscreteMap.maxScreenCol;

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Colors.backgroundColor);

        addKeyListener(keyHandler);
        setFocusable(true);

        // Update State
        update();
    }

    public int getGameLevel() {
        return gameLevel;
    }

    private void setGameLevel(int gameLevel) {
        this.gameLevel = gameLevel;
    }

    public boolean isWin() {
        return win;
    }

    private void setWin(boolean win) {
        this.win = win;
    }

    public void startGame() {
        while (!win) {
            if (keyHandler.isAdvanceTime()) {
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
        keyHandler.reset();
    }

    private void update_components() {
        player.update();
        tileManager.update();
    }

    private void advance_level() {
        DiscreteMapPosition playerPosition = player.getPosition();

        if (playerPosition.equals(DiscreteMap.northEast) && tileManager.getSpellTilesSize() == tileManager.maxSpellTiles) {
            if (getGameLevel() < maxGameLevel) {
                setGameLevel(getGameLevel() + 1);
                reset_components();
            } else {
                setWin(true);
            }
        }
    }

    public void update() {
        update_components();
        advance_level();
    }

    public void reset() {
        setGameLevel(1);
        setWin(false);
        reset_components();
    }

    public void restart() {
        reset();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Draw game entities
        tileManager.draw(g2d);
        player.draw(g2d);
    }
}
