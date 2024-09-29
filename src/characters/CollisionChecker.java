package characters;

import characters.enemies.Enemy;
import main.GamePanel;
import map.DiscreteMapPosition;
import map.tiles.Tile;
import org.jetbrains.annotations.NotNull;

/**
 * Considers if the position an entity is going to move is viable and available.
 * E.g. it is not taken by another entity, and it is not an obstacle on the map.
 */
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

    /**
     * Provide feedback for the choice of a future position. Takes into account the direction
     * of the entity and set its collision property accordingly.
     *
     * @param entity the entity for which to check the future position it wishes for.
     */
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
