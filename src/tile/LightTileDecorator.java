package tile;

import java.awt.*;

public class LightTileDecorator extends TileDecorator {
    public LightTileDecorator(Tile tile) {
        super(tile);

        if (tile.getCollision()) {
            tile.setVisibleColor(new Color(120, 53, 15));
            tile.setInvisibleColor(new Color(124, 45, 18));
        } else if (tile.getClass() == FloorTile.class) {
            tile.setVisibleColor(new Color(252, 211, 77));
            tile.setInvisibleColor(new Color(251, 191, 36));
        }
    }
}
