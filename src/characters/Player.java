package characters;

import main.GamePanel;
import main.colors.UIColors;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import map.Drawable;
import tile.Tile;

import java.awt.*;

public class Player implements Drawable {
    private final GamePanel gamePanel;

    private DiscreteMapPosition playerPos = DiscreteMap.southWest;

    private String direction = "";
    private boolean collision = false;

    public final int visibilityRadius = 6;

    public Player(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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

    public String getDirection() {
        return direction;
    }

    // Move player
    public void moveUp() {
        setPosition(DiscreteMap.getMapPosition(playerPos.getX(), playerPos.getY() - 1));
    }

    public void moveDown() {
        setPosition(DiscreteMap.getMapPosition(playerPos.getX(), playerPos.getY() + 1));
    }

    public void moveRight() {
        setPosition(DiscreteMap.getMapPosition(playerPos.getX() + 1, playerPos.getY()));
    }

    public void moveLeft() {
        setPosition(DiscreteMap.getMapPosition(playerPos.getX() - 1, playerPos.getY()));
    }

    public void update() {
        if (gamePanel.keyHandler.upPressed) {
            direction = "up";
        } else if (gamePanel.keyHandler.downPressed) {
            direction = "down";
        } else if (gamePanel.keyHandler.leftPressed) {
            direction = "left";
        } else if (gamePanel.keyHandler.rightPressed) {
            direction = "right";
        } else {
            direction = "";
        }

        collision = false;
        gamePanel.collisionChecker.checkTile(this);

        if(!collision) {
            switch (direction) {
                case "up": moveUp(); break;
                case "down": moveDown(); break;
                case "left": moveLeft(); break;
                case "right": moveRight(); break;
            }

        }
    }

    public void draw(Graphics2D g2d) {
        // Border
        int x_pos = playerPos.getX_map();
        int y_pos = playerPos.getY_map();

        g2d.setColor(UIColors.borderColor);
        g2d.fillRect(x_pos, y_pos, DiscreteMap.tileSize, DiscreteMap.tileSize);

        // Inner fill
        int x_innerPos = x_pos + Tile.inner_shift;
        int y_innerPos = y_pos + Tile.inner_shift;
        int innerTileSize = DiscreteMap.tileSize - 2 * Tile.inner_shift;

        g2d.setColor(UIColors.playerColor);
        g2d.fillRect(x_innerPos, y_innerPos, innerTileSize, innerTileSize);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerPos=" + playerPos +
                '}';
    }
}
