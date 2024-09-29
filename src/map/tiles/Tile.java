package map.tiles;

import interfaces.Drawable;
import map.DiscreteMapPosition;
import map.tiles.types.FloorTile;
import map.tiles.types.WallTile;
import org.jetbrains.annotations.NotNull;
import ui.Colors;
import ui.Render;

import java.awt.*;

/**
 * Aggregate the map tile logic. Specifically, provide way to handle collisions and
 * visibility status.
 */
abstract public class Tile implements Drawable {
    /**
     * Enum class that assists in converting {@code .txt} files to objects of type Tile.
     */
    public enum TileType {
        FloorTile,
        WallTile;

        /**
         * Get the TileType from an integer. Follows the convention that {@code .txt} files
         * represent:
         * <ul>
         *     <li> FloorTiles with {@code 0} and </li>
         *     <li> WallTiles with {@code 1}.</li>
         * </ul>
         *
         * @param x the integer number.
         * @return the TileType the integer corresponds to.
         */
        public static TileType fromInteger(int x) {
            return TileType.values()[x];
        }
    }

    private final DiscreteMapPosition discreteMapPosition;

    private Color invisibleColor;
    private Color visibleColor;

    private boolean collision;

    private boolean discovered;
    private boolean visible;

    public Tile(DiscreteMapPosition discreteMapPosition) {
        this.discreteMapPosition = discreteMapPosition;
        this.discovered = false;
        this.visible = false;
    }

    /**
     * Create tile based on the TileType. Used for converting {@code .txt} files to
     * Tile objects.
     *
     * @param tileType            the TileType of the tile.
     * @param discreteMapPosition the position on which the tile resides in.
     * @return the Tile object created.
     */
    public static @NotNull Tile createTile(
            @NotNull TileType tileType,
            DiscreteMapPosition discreteMapPosition
    ) {
        return switch (tileType) {
            case TileType.FloorTile -> new FloorTile(discreteMapPosition);
            case TileType.WallTile -> new WallTile(discreteMapPosition);
        };
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return collision;
    }

    public void setInvisibleColor(Color color) {
        invisibleColor = color;
    }

    public void setVisibleColor(Color color) {
        visibleColor = color;
    }

    public DiscreteMapPosition getPosition() {
        return discreteMapPosition;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void toLight() {}


    private void drawUndiscovered(Graphics2D g2d) {
        Render.drawRectangle(g2d, discreteMapPosition, Colors.undiscoveredColor);
    }

    public void drawInvisible(Graphics2D g2d) {
        Render.drawRectangle(g2d, discreteMapPosition, invisibleColor);
    }

    public void drawVisible(Graphics2D g2d) {
        Render.drawRectangle(g2d, discreteMapPosition, visibleColor);
    }

    /**
     * Draw the tile without being concerned for its visibility state.
     * This is handled internally.
     *
     * @param g2d the graphic to draw the component on.
     */
    public void draw(Graphics2D g2d) {
        if (isVisible()) {
            drawVisible(g2d);
        } else if (isDiscovered()) {
            drawInvisible(g2d);
        } else {
            drawUndiscovered(g2d);
        }
    }

    @Override
    public String toString() {
        return "Tile{" +
               "discreteMapPosition=" + discreteMapPosition +
               ", collision=" + collision +
               ", discovered=" + discovered +
               ", visible=" + visible +
               '}';
    }
}
