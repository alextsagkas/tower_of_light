package characters;

import main.GamePanel;
import map.DiscreteMapPosition;
import map.tiles.Tile;
import org.jetbrains.annotations.NotNull;

public final class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    private boolean isAllowedPosition(DiscreteMapPosition position) {
        Tile nearTile = gamePanel.tileManager.getTile(position);
        return !nearTile.getCollision();
    }

    public void checkTile(@NotNull Player player) {
        boolean isAllowed = switch (player.getDirection()) {
            case Direction.UP -> isAllowedPosition(player.getPosition().above());
            case Direction.DOWN -> isAllowedPosition(player.getPosition().below());
            case Direction.LEFT -> isAllowedPosition(player.getPosition().left());
            case Direction.RIGHT -> isAllowedPosition(player.getPosition().right());
            default -> true;
        };
        player.setCollision(!isAllowed);
    }
}
