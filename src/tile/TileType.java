package tile;

public enum TileType {
    FloorTile,
    WallTile;

    public static TileType fromInteger(int x) {
        return TileType.values()[x];
    }
}


