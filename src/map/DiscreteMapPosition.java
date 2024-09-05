package map;

public class DiscreteMapPosition {

    // Position coordinates
    private final int x_map;
    private final int x;
    private final int y_map;
    private final int y;

    protected DiscreteMapPosition(int x, int y) {
        this.x = x;
        this.x_map = x * DiscreteMap.tileSize;
        this.y = y;
        this.y_map = y * DiscreteMap.tileSize;
    }

    // Get coordinates
    public int getX() {
        return x;
    }

    public int getX_map() {
        return x_map;
    }

    public int getY() {
        return y;
    }

    public int getY_map() {
        return y_map;
    }

    public DiscreteMapPosition above() {
        return DiscreteMap.getMapPosition(this.x, this.y - 1);
    }

    public DiscreteMapPosition below() {
        return DiscreteMap.getMapPosition(this.x, this.y + 1);
    }

    public DiscreteMapPosition left() {
        return DiscreteMap.getMapPosition(this.x - 1, this.y);
    }

    public DiscreteMapPosition right() {
        return DiscreteMap.getMapPosition(this.x + 1, this.y);
    }

    public int distanceTo(DiscreteMapPosition other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    @Override
    public String toString() {
        return "DiscreteMapPosition{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}
