package map;

public final class DiscreteMapPosition {
    private final int x;
    private final int xPixel;
    private final int y;
    private final int yPixel;

    private DiscreteMapPosition(int x, int y) {
        this.x = x;
        this.xPixel = x * DiscreteMap.tileSize;
        this.y = y;
        this.yPixel = y * DiscreteMap.tileSize;
    }

    public static DiscreteMapPosition of(int x, int y) {
        if (x >= DiscreteMap.maxScreenCol) {
            x = DiscreteMap.maxScreenCol - 1;
        } else if (x < 0) {
            x = 0;
        }

        if (y >= DiscreteMap.maxScreenRow) {
            y = DiscreteMap.maxScreenRow - 1;
        } else if (y < 0) {
            y = 0;
        }

        return new DiscreteMapPosition(x, y);
    }

    public int getX() {
        return x;
    }

    public int getXPixel() {
        return xPixel;
    }

    public int getY() {
        return y;
    }

    public int getYPixel() {
        return yPixel;
    }

    public DiscreteMapPosition above() {
        return DiscreteMapPosition.of(this.x, this.y - 1);
    }

    public DiscreteMapPosition below() {
        return DiscreteMapPosition.of(this.x, this.y + 1);
    }

    public DiscreteMapPosition left() {
        return DiscreteMapPosition.of(this.x - 1, this.y);
    }

    public DiscreteMapPosition right() {
        return DiscreteMapPosition.of(this.x + 1, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}

        DiscreteMapPosition that = (DiscreteMapPosition) o;
        return getXPixel() == that.getXPixel() && getX() == that.getX() && getYPixel() == that.getYPixel() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        int result = getXPixel();
        result = 31 * result + getX();
        result = 31 * result + getYPixel();
        result = 31 * result + getY();
        return result;
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
