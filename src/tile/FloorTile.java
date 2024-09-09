package tile;

import main.colors.UIColors;
import map.DiscreteMapPosition;

public class FloorTile extends Tile {
    protected FloorTile(DiscreteMapPosition discreteMapPosition) {
        super(discreteMapPosition);

        setInvisibleColor(UIColors.floorTileInvisibleColor);
        setVisibleColor(UIColors.floorTileVisibleColor);

        setCollision(false);
    }
}
