package ui;

import map.DiscreteMap;
import map.DiscreteMapPosition;

import java.awt.*;

public final class Render {
    public static final int inner_shift = 1;

    private static void drawGeneric(
            Graphics2D g2d,
            DiscreteMapPosition position,
            Color innerColor,
            int size,
            int translation
    ) {
        // Border
        int x_pos = position.getXPixel() + translation;
        int y_pos = position.getYPixel() + translation;

        g2d.setColor(Colors.borderColor);
        g2d.fillRect(x_pos, y_pos, size, size);

        // Inner fill
        int x_innerPos = x_pos + inner_shift;
        int y_innerPos = y_pos + inner_shift;
        int innerTileSize = size - 2 * inner_shift;

        g2d.setColor(innerColor);
        g2d.fillRect(x_innerPos, y_innerPos, innerTileSize, innerTileSize);

    }

    public static void drawRectangle(
            Graphics2D g2d,
            DiscreteMapPosition position,
            Color innerColor
    ) {
        drawGeneric(g2d, position, innerColor, DiscreteMap.tileSize, 0);
    }

    public static void drawInnerTopLeftCorner(
            Graphics2D g2d,
            DiscreteMapPosition position,
            Color innerColor
    ) {
        drawGeneric(g2d, position, innerColor, DiscreteMap.itemSize, 0);
    }

    public static void drawInnerBottomRightCorner(
            Graphics2D g2d,
            DiscreteMapPosition position,
            Color innerColor
    ) {
        drawGeneric(g2d, position, innerColor, DiscreteMap.itemSize, +DiscreteMap.tileSize - DiscreteMap.itemSize);
    }

}
