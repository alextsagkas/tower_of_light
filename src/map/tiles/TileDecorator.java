package map.tiles;

import map.DiscreteMapPosition;

import java.awt.*;

abstract public class TileDecorator extends Tile {
    Tile tile;

    public TileDecorator(Tile tile) {
        super(tile.getPosition());
        this.tile = tile;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Tile otherTile) {
            return otherTile.getPosition() == tile.getPosition();
        }

        return false;
    }

    @Override
    public void setCollision(boolean collision) {
        tile.setCollision(collision);
    }

    @Override
    public boolean getCollision() {
        return tile.getCollision();
    }

    @Override
    public void setInvisibleColor(Color color) {
        tile.setInvisibleColor(color);
    }

    @Override
    public void setVisibleColor(Color color) {
        tile.setVisibleColor(color);
    }

    @Override
    public DiscreteMapPosition getPosition() {
        return tile.getPosition();
    }

    @Override
    public boolean isDiscovered() {
        return tile.isDiscovered();
    }

    @Override
    public void setDiscovered(boolean discovered) {
        tile.setDiscovered(discovered);
    }

    @Override
    public void setVisible(boolean visible) {
        tile.setVisible(visible);
    }

    @Override
    public boolean isVisible() {
        return tile.isVisible();
    }

    @Override
    public void toLight() {
        tile.toLight();
    }

    @Override
    public void draw(Graphics2D g2d) {
        tile.draw(g2d);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
