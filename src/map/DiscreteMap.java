package map;

public final class DiscreteMap {
    // Screen settings
    public static final int tileSize = 16;
    public static final int itemSize = 7;
    public static final int maxScreenCol = 52;
    public static final int maxScreenRow = maxScreenCol;

    // Map corners
    public static final DiscreteMapPosition southWest = DiscreteMapPosition.of(1, maxScreenRow - 2);
    public static final DiscreteMapPosition northEast = DiscreteMapPosition.of(maxScreenCol - 2, 1);

    private DiscreteMap() {}
}
