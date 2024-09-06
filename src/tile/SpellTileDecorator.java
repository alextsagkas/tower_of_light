package tile;

import java.awt.*;

public class SpellTileDecorator extends TileDecorator {
    public SpellTileDecorator(Tile tile) {
        super(tile);

        tile.setInvisibleColor(new Color(217, 119, 6));
        tile.setVisibleColor(new Color(245, 158, 11));
    }
}
