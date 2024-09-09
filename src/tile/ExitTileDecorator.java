package tile;

import java.awt.*;

public class ExitTileDecorator extends TileDecorator {
    public ExitTileDecorator(Tile tile) {
        super(tile);

        tile.setInvisibleColor(new Color(190, 242, 100));
        tile.setVisibleColor(new Color(217, 249, 157));
    }
}
