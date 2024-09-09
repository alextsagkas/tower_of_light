package tile;

import main.colors.UIColors;

public class LightTileDecorator extends TileDecorator {
    public LightTileDecorator(Tile tile) {
        super(tile);

        if (tile.getCollision()) {
            tile.setVisibleColor(UIColors.lightWallTileVisibleColor);
            tile.setInvisibleColor(UIColors.lightWallTileInvisibleColor);
        } else if (tile.getClass() == FloorTile.class) {
            tile.setVisibleColor(UIColors.lightFloorTileVisibleColor);
            tile.setInvisibleColor(UIColors.lightFloorTileInvisibleColor);
        }
    }
}
