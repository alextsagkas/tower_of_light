package map;

public final class DiscreteMap {
    // Screen settings
    public static final int tileSize = 16;
    public static final int maxScreenCol = 52;
    public static final int maxScreenRow = maxScreenCol;

    // Map corners
    public static final DiscreteMapPosition southWest = new DiscreteMapPosition(
             1, maxScreenRow - 2
    );
    public static final DiscreteMapPosition northEast = new DiscreteMapPosition(
            maxScreenCol - 2, 1
    );

    private DiscreteMap() {
    }

    public static DiscreteMapPosition getMapPosition(int x, int y) {
        // Out of bounds
        if (x >= maxScreenCol) {
            x = maxScreenCol - 1;
        } else if (x < 0) {
            x = 0;
        }

        if (y >= maxScreenRow) {
            y = maxScreenRow - 1;
        } else if (y < 0) {
            y = 0;
        }

        return new DiscreteMapPosition(x, y);
    }
}
