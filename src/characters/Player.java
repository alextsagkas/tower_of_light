package characters;

import interfaces.*;
import main.GamePanel;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import map.tiles.TileManager;
import ui.Colors;
import ui.Render;

import java.awt.*;

public final class Player implements Drawable, Updatable, Resettable, LogSubject, StatSubject {
    private final GamePanel gamePanel;
    private DiscreteMapPosition playerPos;
    private Direction direction;
    private boolean collision;
    public final int visibilityRadius;
    private int actionsNumber;
    private LogObserver logObserver;
    private StatObserver statObserver;

    public Player(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.playerPos = DiscreteMap.southWest;
        this.direction = Direction.NONE;
        this.collision = false;
        this.visibilityRadius = 6;
        this.actionsNumber = 0;
    }

    public void setPosition(DiscreteMapPosition pos) {
        this.playerPos = pos;
    }

    public DiscreteMapPosition getPosition() {
        return playerPos;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return collision;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setActionsNumber(int actionsNumber) {
        this.actionsNumber = actionsNumber;
    }

    public int getActionsNumber() {
        return actionsNumber;
    }

    public void attach(LogObserver logObserver) {
        this.logObserver = logObserver;
    }

    public void notifyObserver(String log) {
        logObserver.update(log);
    }

    public void attach(StatObserver statObserver) {
        this.statObserver = statObserver;
    }

    public void notifyObserver() {
        statObserver.update();
    }

    private void moveUp() {
        setPosition(getPosition().above());
        setActionsNumber(getActionsNumber() + 1);
    }

    private void moveDown() {
        setPosition(getPosition().below());
        setActionsNumber(getActionsNumber() + 1);
    }

    private void moveRight() {
        setPosition(getPosition().right());
        setActionsNumber(getActionsNumber() + 1);
    }

    private void moveLeft() {
        setPosition(getPosition().left());
        setActionsNumber(getActionsNumber() + 1);
    }

    public void reset() {
        setPosition(DiscreteMap.southWest);
        setDirection(Direction.NONE);
        setCollision(false);
        setActionsNumber(0);
    }

    private void move() {
        if (gamePanel.keyHandler.isUpPressed()) {
            setDirection(Direction.UP);
        } else if (gamePanel.keyHandler.isDownPressed()) {
            setDirection(Direction.DOWN);
        } else if (gamePanel.keyHandler.isLeftPressed()) {
            setDirection(Direction.LEFT);
        } else if (gamePanel.keyHandler.isRightPressed()) {
            setDirection(Direction.RIGHT);
        } else {
            setDirection(Direction.NONE);
        }

        gamePanel.collisionChecker.checkTile(this);

        if (!collision) {
            switch (direction) {
                case Direction.UP:
                    moveUp();
                    break;
                case Direction.DOWN:
                    moveDown();
                    break;
                case Direction.LEFT:
                    moveLeft();
                    break;
                case Direction.RIGHT:
                    moveRight();
                    break;
            }
        }
    }

    private void executeBeacon() {
        if (gamePanel.keyHandler.isCastBeacon()) {
            TileManager tileManager = gamePanel.tileManager;

            int actionCooldownNumber = 15;
            int minSeparation = tileManager.beaconTileMinSeparation(playerPos);
            int beaconTilesSize = gamePanel.tileManager.getBeaconTilesSize();
            int maxBeaconTiles = gamePanel.tileManager.maxBeaconTiles;

            if (
                    minSeparation >= tileManager.beaconTileSeparation &&
                    getActionsNumber() >= actionCooldownNumber &&
                    beaconTilesSize < maxBeaconTiles
            ) {
                tileManager.drawBeacon(getPosition());
                setActionsNumber(0);
            } else if (beaconTilesSize < maxBeaconTiles) {
                notifyLogObserver("Beacon could not be created, because:");
                if (minSeparation < tileManager.beaconTileSeparation) {
                    notifyLogObserver(String.format(
                            "- Shortest distance = %d < %d.",
                            minSeparation,
                            tileManager.beaconTileSeparation
                    ));
                }
                if (getActionsNumber() < actionCooldownNumber) {
                    notifyLogObserver(String.format(
                            "- Action cooldown = %d < %d.",
                            getActionsNumber(),
                            actionCooldownNumber
                    ));
                }
            }
        }
    }

    public void update() {
        move();
        executeBeacon();
    }

    public void draw(Graphics2D g2d) {
        Render.drawRectangle(g2d, getPosition(), Colors.playerColor);
    }

    @Override
    public String toString() {
        return "Player{" +
               "collision=" + collision +
               ", playerPos=" + playerPos +
               ", direction=" + direction +
               '}';
    }
}
