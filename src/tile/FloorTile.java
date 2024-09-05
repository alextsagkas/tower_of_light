package tile;

import map.DiscreteMapPosition;

import java.awt.*;

public class FloorTile extends Tile {
    protected FloorTile(DiscreteMapPosition discreteMapPosition) {
        super(discreteMapPosition);

        setInvisibleColor(new Color(0x64748b));
        setVisibleColor(new Color(0x94a3b8));
        setCollision(false);
    }
}
