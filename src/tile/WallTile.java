package tile;

import java.awt.*;

public class WallTile extends Tile {
    protected WallTile(
            int x,
            int y,
            int tileSize
    ) {
        super(x, y, tileSize);

        setInvisibleColor(new Color(0x1e293b));
        setVisibleColor(new Color(0x334155));
    }
}
