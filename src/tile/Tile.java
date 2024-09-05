package tile;

import map.DiscreteMap;
import map.DiscreteMapPosition;
import map.Drawable;

import java.awt.*;

abstract public class Tile {
    private final DiscreteMapPosition discreteMapPosition;

    public static final int inner_shift = 1;

    public static final Color borderColor = new Color(0x020617);
    private final Color undiscoveredColor;
    private Color invisibleColor;
    private Color visibleColor;

    private boolean collision;

    public Tile(DiscreteMapPosition discreteMapPosition) {
        this.discreteMapPosition = discreteMapPosition;

        undiscoveredColor = new Color(0x0f172a);
    }

    public static Tile createTile(
            TileType tileType,
            DiscreteMapPosition discreteMapPosition
    ) {
        return switch (tileType) {
            case TileType.FloorTile -> new FloorTile(discreteMapPosition);
            case TileType.WallTile -> new WallTile(discreteMapPosition);
            case null -> null;
        };
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean getCollision() {
        return collision;
    }

    protected void setInvisibleColor(Color color) {
        invisibleColor = color;
    }

    protected void setVisibleColor(Color color) {
        visibleColor = color;
    }

    private void draw(
            Graphics2D g2d,
            Color innerColor
    ) {
        // Border
        int x_pos = discreteMapPosition.getX_map();
        int y_pos = discreteMapPosition.getY_map();

        g2d.setColor(borderColor);
        g2d.fillRect(x_pos, y_pos, DiscreteMap.tileSize, DiscreteMap.tileSize);

        // Inner fill
        int x_innerPos = x_pos + inner_shift;
        int y_innerPos = y_pos + inner_shift;
        int innerTileSize = DiscreteMap.tileSize - 2 * inner_shift;

        g2d.setColor(innerColor);
        g2d.fillRect(x_innerPos, y_innerPos, innerTileSize, innerTileSize);
    }

    public void drawUndiscovered(Graphics2D g2d) {
        draw(g2d, undiscoveredColor);
    }

    public void drawInvisible(Graphics2D g2d) {
        draw(g2d, invisibleColor);
    }

    public void drawVisible(Graphics2D g2d) {
        draw(g2d, visibleColor);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "discreteMapPosition=" + discreteMapPosition +
                '}';
    }
}