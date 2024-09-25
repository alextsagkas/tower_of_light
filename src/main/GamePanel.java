package main;

import characters.CollisionChecker;
import characters.enemies.EnemyManager;
import characters.player.Player;
import interfaces.LogObserver;
import interfaces.LogSubject;
import interfaces.Resettable;
import interfaces.Updatable;
import items.ItemManager;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import map.tiles.TileManager;
import ui.Colors;

import javax.swing.*;
import java.awt.*;

public final class GamePanel extends JPanel implements Updatable, Resettable, LogSubject {
    private int gameLevel;
    public final int maxGameLevel;
    private boolean win;
    private boolean gameOver;

    public final Game game;
    public final TileManager tileManager;
    public final KeyHandler keyHandler;
    public final Player player;
    public final CollisionChecker collisionChecker;
    public final ItemManager itemManager;
    public final EnemyManager enemyManager;
    private LogObserver logObserver;

    public GamePanel(Game game) {
        // Initialize attributes
        gameLevel = 1;
        maxGameLevel = 6;
        win = false;
        gameOver = false;

        this.game = game;
        this.tileManager = new TileManager(this);
        this.keyHandler = new KeyHandler();
        this.player = new Player(this, Game.getRace(), Game.getWarrior());
        this.collisionChecker = new CollisionChecker(this);
        this.itemManager = new ItemManager(this);
        this.enemyManager = new EnemyManager(this);

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

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void attachLogObserver(LogObserver logObserver) {
        this.logObserver = logObserver;
    }

    public void notifyLogObserver(String log) {
        logObserver.updateLog(log);
    }

    private void attachObservers() {
        this.attachLogObserver(game.gameLog);
        keyHandler.attachLogObserver(game.gameLog);
        player.attachLogObserver(game.gameLog);
        player.attachStatObserver(game.playerStats);
        tileManager.attachLogObserver(game.gameLog);
        itemManager.attachLogObserver(game.gameLog);
        enemyManager.attachLogObserver(game.gameLog);
        enemyManager.attachStatObserver(game.enemiesLog);
    }

    public void startGame() {
        attachObservers();

        notifyLogObserver(String.format("The game has started on level %d.", getGameLevel()));

        while (!win && !gameOver) {
            if (keyHandler.isAdvanceTime()) {
                // Bound to discrete time
                update();
                // Draw the screen with updated information
                repaint();
            } else if (keyHandler.isAdvanceFreeActionTime()) {
                // Unbound of time
                freeActions();
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
        itemManager.reset();
        enemyManager.reset();
    }

    private void restart_components() {
        player.restart();
        tileManager.restart();
        keyHandler.restart();
        itemManager.restart();
        enemyManager.restart();
    }

    private void update_components() {
        player.update();
        tileManager.update();
        itemManager.update();
        enemyManager.update();
    }

    private void advance_level() {
        DiscreteMapPosition playerPosition = player.getPosition();

        if (playerPosition.equals(DiscreteMap.northEast) &&
            tileManager.getBeaconTilesSize() == tileManager.maxBeaconTiles) {
            if (getGameLevel() < maxGameLevel) {
                setGameLevel(getGameLevel() + 1);
                reset_components();
                notifyLogObserver(String.format("The game level is %d/%d.", getGameLevel(), maxGameLevel));
            } else {
                setWin(true);
            }
        }
    }

    public void update() {
        update_components();
        advance_level();
    }

    public void freeActions() {
        player.freeActions();
    }

    public void reset() {
        setGameLevel(1);
        setWin(false);
        setGameOver(false);
        reset_components();
    }

    @Override
    public void restart() {
        setGameLevel(1);
        setWin(false);
        setGameOver(false);
        restart_components();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Draw game entities
        tileManager.draw(g2d);
        itemManager.draw(g2d);
        enemyManager.draw(g2d);
        player.draw(g2d);
    }
}
