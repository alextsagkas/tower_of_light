package main;

import characters.Player;
import map.DiscreteMapPosition;
import tile.Tile;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Player player) {
        switch (player.getDirection()) {
            case "up":
                DiscreteMapPosition abovePlayer = player.getPosition().above();
                Tile aboveTile = gamePanel.tileManager.getTile(abovePlayer);
                if (aboveTile.getCollision()) {
                    player.setCollision(true);
                }
                break;
            case "down":
                DiscreteMapPosition belowPlayer = player.getPosition().below();
                Tile belowTile = gamePanel.tileManager.getTile(belowPlayer);
                if (belowTile.getCollision()) {
                    player.setCollision(true);
                }
                break;
            case "left":
                DiscreteMapPosition leftPlayer = player.getPosition().left();
                Tile leftTile = gamePanel.tileManager.getTile(leftPlayer);
                if (leftTile.getCollision()) {
                    player.setCollision(true);
                }
                break;
            case "right":
                DiscreteMapPosition rightPlayer = player.getPosition().right();
                Tile rightTile = gamePanel.tileManager.getTile(rightPlayer);
                if (rightTile.getCollision()) {
                    player.setCollision(true);
                }
                break;
        }

    }
}
