package map.tiles.decorators;

import map.tiles.Tile;
import map.tiles.TileDecorator;
import ui.Colors;

public final class BeaconTileDecorator extends TileDecorator {
    public BeaconTileDecorator(Tile tile) {
        super(tile);

        tile.setInvisibleColor(Colors.spellTileInvisibleColor);
        tile.setVisibleColor(Colors.spellTileVisibleColor);
    }

    @Override
    public void toLight() {}
}
