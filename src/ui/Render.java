package ui;

import map.DiscreteMap;
import map.DiscreteMapPosition;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Application wide class to provide drawing/rendering functionality to all drawable
 * components of the game.
 */
public final class Render {
    /**
     * The shift amount that dictated the size of border around objects (rectangles).
     */
    private static final int inner_shift = 1;

    /**
     * Draw on provided graphic a bordered rectangle.
     *
     * @param g2d         the graphic on which to draw the rectangle.
     * @param position    the south-west side of the rectangle drawn.
     * @param innerColor  the color of the inner rectangle (not the border).
     * @param size        the size of the rectangle.
     * @param translation the translation applied to the rectangle.
     */
    private static void drawGeneric(
            @NotNull Graphics2D g2d,
            @NotNull DiscreteMapPosition position,
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

    /**
     * Draw a bordered rectangle on provided graphic.
     *
     * @param g2d        the graphic on which to draw the rectangle.
     * @param position   the position of the south-west side of the rectangle.
     * @param innerColor the color of the inner rectangle (not the border).
     */
    public static void drawRectangle(
            Graphics2D g2d,
            DiscreteMapPosition position,
            Color innerColor
    ) {
        drawGeneric(g2d, position, innerColor, DiscreteMap.tileSize, 0);
    }

    /**
     * Draw a rectangle on the inner top left corner of a tile (full-sized rectangle).
     *
     * @param g2d        the graphic on which to draw the rectangle.
     * @param position   the position of the south-west side of the full-sized rectangle.
     * @param innerColor the color of the inner rectangle (not the border).
     */
    public static void drawInnerTopLeftCorner(
            Graphics2D g2d,
            DiscreteMapPosition position,
            Color innerColor
    ) {
        drawGeneric(g2d, position, innerColor, DiscreteMap.itemSize, 0);
    }

    /**
     * Draw a rectangle on the inner bottom right corner of a tile (full-sized rectangle).
     *
     * @param g2d        the graphic on which to draw the rectangle.
     * @param position   the position of the south-west side of the full-sized rectangle.
     * @param innerColor the color of the inner rectangle (not the border).
     */
    public static void drawInnerBottomRightCorner(
            Graphics2D g2d,
            DiscreteMapPosition position,
            Color innerColor
    ) {
        drawGeneric(g2d, position, innerColor, DiscreteMap.itemSize, DiscreteMap.tileSize - DiscreteMap.itemSize);
    }

    private Render() {}
}
