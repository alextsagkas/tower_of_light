package map.tiles.decorators;

import map.tiles.Tile;
import map.tiles.TileDecorator;
import ui.Colors;

public final class ExitTileDecorator extends TileDecorator {
    public ExitTileDecorator(Tile tile) {
        super(tile);

        tile.setInvisibleColor(Colors.exitTileInvisibleColor);
        tile.setVisibleColor(Colors.exitTileVisibleColor);
    }

    @Override
    public void toLight() {}
}
