package tile;

import main.colors.UIColors;

public class ExitTileDecorator extends TileDecorator {
    public ExitTileDecorator(Tile tile) {
        super(tile);

        tile.setInvisibleColor(UIColors.exitTileInvisibleColor);
        tile.setVisibleColor(UIColors.exitTileVisibleColor);
    }
}
