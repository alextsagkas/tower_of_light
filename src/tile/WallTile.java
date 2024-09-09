package tile;

import main.colors.UIColors;
import map.DiscreteMapPosition;

public class WallTile extends Tile {
    protected WallTile(DiscreteMapPosition discreteMapPosition) {
        super(discreteMapPosition);

        setInvisibleColor(UIColors.wallTileInvisibleColor);
        setVisibleColor(UIColors.wallTileVisibleColor);

        setCollision(true);
    }
}
