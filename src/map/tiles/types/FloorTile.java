package map.tiles.types;

import map.DiscreteMapPosition;
import map.tiles.Tile;
import ui.Colors;

public final class FloorTile extends Tile {
    public FloorTile(DiscreteMapPosition discreteMapPosition) {
        super(discreteMapPosition);

        setInvisibleColor(Colors.floorTileInvisibleColor);
        setVisibleColor(Colors.floorTileVisibleColor);

        setCollision(false);
    }

    @Override
    public void toLight() {
        super.toLight();

        setInvisibleColor(Colors.lightFloorTileInvisibleColor);
        setVisibleColor(Colors.lightFloorTileVisibleColor);
    }
}
