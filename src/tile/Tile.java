package tile;

import main.colors.UIColors;
import map.DiscreteMap;
import map.DiscreteMapPosition;
import interfaces.Drawable;

import java.awt.*;

abstract public class Tile implements Drawable {
    private final DiscreteMapPosition discreteMapPosition;

    public static final int inner_shift = 1;
    private Color invisibleColor;
    private Color visibleColor;

    private boolean collision;

    private boolean discovered;
    private boolean visible;

    public Tile(DiscreteMapPosition discreteMapPosition) {
        this.discreteMapPosition = discreteMapPosition;
        discovered = false;
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

    private void drawGeneric(
            Graphics2D g2d,
            Color innerColor
    ) {
        // Border
        int x_pos = discreteMapPosition.getX_map();
        int y_pos = discreteMapPosition.getY_map();

        g2d.setColor(UIColors.borderColor);
        g2d.fillRect(x_pos, y_pos, DiscreteMap.tileSize, DiscreteMap.tileSize);

        // Inner fill
        int x_innerPos = x_pos + inner_shift;
        int y_innerPos = y_pos + inner_shift;
        int innerTileSize = DiscreteMap.tileSize - 2 * inner_shift;

        g2d.setColor(innerColor);
        g2d.fillRect(x_innerPos, y_innerPos, innerTileSize, innerTileSize);
    }

    private void drawUndiscovered(Graphics2D g2d) {
        drawGeneric(g2d, UIColors.undiscoveredColor);
    }

    private void drawInvisible(Graphics2D g2d) {
        drawGeneric(g2d, invisibleColor);
    }

    private void drawVisible(Graphics2D g2d) {
        drawGeneric(g2d, visibleColor);
    }

    public void draw(Graphics2D g2d) {
        if (visible) {
            drawVisible(g2d);
        } else if (discovered) {
            drawInvisible(g2d);
        } else {
            drawUndiscovered(g2d);
        }
    }

    @Override
    public String toString() {
        return "Tile{" +
                "visible=" + visible +
                ", discovered=" + discovered +
                ", discreteMapPosition=" + discreteMapPosition +
                '}';
    }
}