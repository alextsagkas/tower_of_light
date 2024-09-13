package characters;

import interfaces.Drawable;
import interfaces.Resettable;
import interfaces.Updatable;
import main.GamePanel;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import ui.Colors;
import ui.Render;

import java.awt.*;

public final class Player implements Drawable, Updatable, Resettable {
    private final GamePanel gamePanel;
    private DiscreteMapPosition playerPos;
    private Direction direction;
    private boolean collision;
    public final int visibilityRadius;

    public Player(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.playerPos = DiscreteMap.southWest;
        this.direction = Direction.NONE;
        this.collision = false;
        this.visibilityRadius = 6;
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

    // Move player
    public void moveUp() {
        setPosition(getPosition().above());
    }

    public void moveDown() {
        setPosition(getPosition().below());
    }

    public void moveRight() {
        setPosition(getPosition().right());
    }

    public void moveLeft() {
        setPosition(getPosition().left());
    }

    public void reset() {
        setDirection(Direction.NONE);
        setCollision(false);
        setPosition(DiscreteMap.southWest);
    }

    public void update() {
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

    public void draw(Graphics2D g2d) {
        Render.drawRectangle(g2d, getPosition(), Colors.playerColor);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerPos=" + getPosition() +
                '}';
    }
}
