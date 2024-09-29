package map;

/**
 * Gather all the map information/constants in a single class.
 */
public final class DiscreteMap {
    /**
     * The tile size in pixels.
     */
    public static final int tileSize = 16;
    /**
     * The size of items on the map.
     */
    public static final int itemSize = 7;
    /**
     * The number of tiles on the columns of the map.
     */
    public static final int maxScreenCol = 52;
    /**
     * The number of tiles on the rows of the map.
     */
    public static final int maxScreenRow = maxScreenCol;

    /**
     * The south-west corner of the map, where player spawns.
     */
    public static final DiscreteMapPosition southWest = DiscreteMapPosition.of(1, maxScreenRow - 2);
    /**
     * The north-east corner of the map, where player advances in next level.
     */
    public static final DiscreteMapPosition northEast = DiscreteMapPosition.of(maxScreenCol - 2, 1);

    private DiscreteMap() {}
}
