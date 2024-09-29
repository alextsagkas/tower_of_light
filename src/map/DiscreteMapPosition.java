package map;

import characters.Direction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Abstraction of positions on the game map. Useful for drawing components and
 * also thinking about their position in a discrete manner and not per-pixel basis.
 */
public final class DiscreteMapPosition {
    private final int x;
    private final int xPixel;
    private final int y;
    private final int yPixel;

    /**
     * Create a position.
     *
     * @param x the abstract x position on the grid.
     * @param y the abstract y position on the grid.
     */
    private DiscreteMapPosition(int x, int y) {
        this.x = x;
        this.xPixel = x * DiscreteMap.tileSize;
        this.y = y;
        this.yPixel = y * DiscreteMap.tileSize;
    }

    /**
     * Use it to generate a new position. Takes into account the
     * map limits and ensures to never get over them.
     *
     * @param x the abstract x position on the grid.
     * @param y the abstract y position on the grid.
     * @return the asked for discrete map position.
     */
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

    /**
     * Get the abstract x-position of the object on the grid.
     *
     * @return the x-position.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the x-position in pixels.
     *
     * @return the x-position.
     */
    public int getXPixel() {
        return xPixel;
    }

    /**
     * Get the abstract y-position of the object on the grid.
     *
     * @return the y-position.
     */
    public int getY() {
        return y;
    }

    /**
     * Get the y-position in pixels.
     *
     * @return the y-position.
     */
    public int getYPixel() {
        return yPixel;
    }

    /**
     * Calculate the position above the current one.
     *
     * @return the above position.
     */
    @Contract(" -> new")
    public @NotNull DiscreteMapPosition above() {
        return DiscreteMapPosition.of(this.x, this.y - 1);
    }

    /**
     * Calculate the position below the current one.
     *
     * @return the below position.
     */
    @Contract(" -> new")
    public @NotNull DiscreteMapPosition below() {
        return DiscreteMapPosition.of(this.x, this.y + 1);
    }

    /**
     * Calculate the position on the left the current one.
     *
     * @return the left position.
     */
    @Contract(" -> new")
    public @NotNull DiscreteMapPosition left() {
        return DiscreteMapPosition.of(this.x - 1, this.y);
    }

    /**
     * Calculate the position on the right the current one.
     *
     * @return the right position.
     */
    @Contract(" -> new")
    public @NotNull DiscreteMapPosition right() {
        return DiscreteMapPosition.of(this.x + 1, this.y);
    }

    /**
     * Calculate the Manhattan distance to another abstract position.
     *
     * @param other the other abstract position.
     * @return the distance between the two positions.
     */
    public int distanceTo(@NotNull DiscreteMapPosition other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    /**
     * Get the direction on which if {@code this} moves will reduce the distance between it
     * and {@code other} the most.
     *
     * @param other a position on the map.
     * @return the direction on which the distance between {@code this} and {@code other} will
     * have the least value.
     */
    public Direction closestDirectionTo(@NotNull DiscreteMapPosition other) {
        DiscreteMapPosition above = this.above();
        DiscreteMapPosition below = this.below();
        DiscreteMapPosition left = this.left();
        DiscreteMapPosition right = this.right();

        Direction closestDirection = Direction.UP;
        int minDistance = above.distanceTo(other);

        if (below.distanceTo(other) <= minDistance) {
            closestDirection = Direction.DOWN;
            minDistance = below.distanceTo(other);
        }
        if (left.distanceTo(other) <= minDistance) {
            closestDirection = Direction.LEFT;
            minDistance = left.distanceTo(other);
        }
        if (right.distanceTo(other) <= minDistance) {
            closestDirection = Direction.RIGHT;
        }

        return closestDirection;
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
