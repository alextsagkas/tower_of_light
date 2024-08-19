package tile;

import java.awt.*;

abstract public class Tile {
    private final int x;
    private final int y;
    private final int tileSize;
    private final int inner_shift;

    private final Color borderColor;
    private final Color undiscoveredColor;
    private Color invisibleColor;
    private Color visibleColor;

    public Tile(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;

        inner_shift = 1;
        borderColor = new Color(0x020617);
        undiscoveredColor = new Color(0x0f172a);
    }

    public static Tile createTile(
            TileType tileType,
            int x,
            int y,
            int tileSize
    ) {
        return switch (tileType) {
            case TileType.FloorTile -> new FloorTile(x,y,tileSize);
            case TileType.WallTile -> new WallTile(x,y,tileSize);
            case null -> null;
        };
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
        int x_pos = x * tileSize;
        int y_pos = y * tileSize;

        g2d.setColor(borderColor);
        g2d.fillRect(x_pos, y_pos, tileSize, tileSize);

        // Inner fill
        int x_innerPos = x_pos + inner_shift;
        int y_innerPos = y_pos + inner_shift;
        int innerTileSize = tileSize - 2 * inner_shift;

        g2d.setColor(innerColor);
        g2d.fillRect(x_innerPos, y_innerPos, innerTileSize, innerTileSize);
    }

    public void drawUndiscovered(Graphics2D g2d){
        draw(g2d, undiscoveredColor);
    }

    public void drawInvisible(Graphics2D g2d){
        draw(g2d, invisibleColor);
    }

    public void drawVisible(Graphics2D g2d){
        draw(g2d, visibleColor);
    }
}