package tile;

import main.colors.UIColors;

public class SpellTileDecorator extends TileDecorator {
    public SpellTileDecorator(Tile tile) {
        super(tile);

        tile.setInvisibleColor(UIColors.spellTileInvisibleColor);
        tile.setVisibleColor(UIColors.spellTileVisibleColor);
    }
}
