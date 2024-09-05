package tile;

import map.DiscreteMapPosition;

import java.awt.*;

public class WallTile extends Tile {
    protected WallTile(DiscreteMapPosition discreteMapPosition) {
        super(discreteMapPosition);

        setInvisibleColor(new Color(0x1e293b));
        setVisibleColor(new Color(0x334155));
        setCollision(true);
    }
}
