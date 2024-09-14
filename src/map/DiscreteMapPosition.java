package map;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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

    @Contract(value = "_, _ -> new")
    public static @NotNull DiscreteMapPosition of(int x, int y) {
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

    @Contract(" -> new")
    public @NotNull DiscreteMapPosition above() {
        return DiscreteMapPosition.of(this.x, this.y - 1);
    }

    @Contract(" -> new")
    public @NotNull DiscreteMapPosition below() {
        return DiscreteMapPosition.of(this.x, this.y + 1);
    }

    @Contract(" -> new")
    public @NotNull DiscreteMapPosition left() {
        return DiscreteMapPosition.of(this.x - 1, this.y);
    }

    @Contract(" -> new")
    public @NotNull DiscreteMapPosition right() {
        return DiscreteMapPosition.of(this.x + 1, this.y);
    }

    public int distanceTo(@NotNull DiscreteMapPosition other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}

        DiscreteMapPosition that = (DiscreteMapPosition) o;
        return getX() == that.getX() && xPixel == that.xPixel && getY() == that.getY() && yPixel == that.yPixel;
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + xPixel;
        result = 31 * result + getY();
        result = 31 * result + yPixel;
        return result;
    }

    @Override
    public @NotNull String toString() {
        return "DiscreteMapPosition{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}
