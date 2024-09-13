package map.tiles.types;

import map.DiscreteMapPosition;
import map.tiles.Tile;
import ui.Colors;

public final class WallTile extends Tile {
    public WallTile(DiscreteMapPosition discreteMapPosition) {
        super(discreteMapPosition);

        setInvisibleColor(Colors.wallTileInvisibleColor);
        setVisibleColor(Colors.wallTileVisibleColor);

        setCollision(true);
    }

    @Override
    public void toLight() {
        super.toLight();

        setInvisibleColor(Colors.lightWallTileInvisibleColor);
        setVisibleColor(Colors.lightWallTileVisibleColor);
    }
}
