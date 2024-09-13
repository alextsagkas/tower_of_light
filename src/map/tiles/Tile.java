package map.tiles;

import interfaces.Drawable;
import map.DiscreteMapPosition;
import map.tiles.types.FloorTile;
import map.tiles.types.WallTile;
import ui.Colors;
import ui.Render;

import java.awt.*;

abstract public class Tile implements Drawable {
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

    public static Tile createTile(
            TileType tileType,
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

    private void drawInvisible(Graphics2D g2d) {
        Render.drawRectangle(g2d, discreteMapPosition, invisibleColor);
    }

    private void drawVisible(Graphics2D g2d) {
        Render.drawRectangle(g2d, discreteMapPosition, visibleColor);
    }

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
                "visible=" + visible +
                ", discovered=" + discovered +
                ", discreteMapPosition=" + discreteMapPosition +
                '}';
    }
}
