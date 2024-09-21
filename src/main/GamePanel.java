package main;

import characters.CollisionChecker;
import characters.player.Player;
import interfaces.LogObserver;
import interfaces.LogSubject;
import interfaces.Resettable;
import interfaces.Updatable;
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

    public final Game game;
    public final TileManager tileManager;
    public final KeyHandler keyHandler;
    public final Player player;
    public final CollisionChecker collisionChecker;
    private LogObserver logObserver;

    public GamePanel(Game game) {
        // Initialize attributes
        gameLevel = 1;
        maxGameLevel = 6;
        win = false;

        this.game = game;
        this.tileManager = new TileManager(this);
        this.keyHandler = new KeyHandler();
        this.player = new Player(this, Game.getRace(), Game.getWarrior());
        this.collisionChecker = new CollisionChecker(this);

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
    }

    public void startGame() {
        attachObservers();

        notifyLogObserver(String.format("The game has started on level %d.", getGameLevel()));

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

    private void restart_components() {
        player.restart();
        tileManager.restart();
        keyHandler.restart();
    }

    private void update_components() {
        player.update();
        tileManager.update();
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

    public void reset() {
        setGameLevel(1);
        setWin(false);
        reset_components();
    }

    @Override
    public void restart() {
        setGameLevel(1);
        setWin(false);
        restart_components();
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
