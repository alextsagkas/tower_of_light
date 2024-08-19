package tile;

import java.awt.*;

public class FloorTile extends Tile {
    protected FloorTile(
            int x,
            int y,
            int tileSize
    ) {
        super(x, y, tileSize);

        setInvisibleColor(new Color(0x64748b));
        setVisibleColor(new Color(0x94a3b8));
    }
}
