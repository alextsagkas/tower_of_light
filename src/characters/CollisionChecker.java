package characters;

import characters.enemies.Enemy;
import main.GamePanel;
import map.DiscreteMapPosition;
import map.tiles.Tile;
import org.jetbrains.annotations.NotNull;

public final class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean checkTileAllowed(DiscreteMapPosition position) {
        Tile nearTile = gamePanel.tileManager.getTile(position);
        return !nearTile.getCollision();
    }

    private boolean checkPlayerAllowed(DiscreteMapPosition position) {
        DiscreteMapPosition playerPosition = gamePanel.player.getPosition();
        return !playerPosition.equals(position);
    }

    private boolean checkEnemiesAllowed(DiscreteMapPosition position) {
        for (Enemy enemy : gamePanel.enemyManager.getGeneratedEnemies()) {
            if (enemy.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllowedPosition(DiscreteMapPosition position) {
        if (!checkTileAllowed(position)) {return false;}
        if (!checkPlayerAllowed(position)) {return false;}
        if (!checkEnemiesAllowed(position)) {return false;}
        return true;
    }

    public void checkPosition(@NotNull Entity entity) {
        boolean isAllowed = switch (entity.getDirection()) {
            case Direction.UP -> isAllowedPosition(entity.getPosition().above());
            case Direction.DOWN -> isAllowedPosition(entity.getPosition().below());
            case Direction.LEFT -> isAllowedPosition(entity.getPosition().left());
            case Direction.RIGHT -> isAllowedPosition(entity.getPosition().right());
            default -> true;
        };
        entity.setCollision(!isAllowed);
    }
}
