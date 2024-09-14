package ui;

import map.DiscreteMap;
import map.DiscreteMapPosition;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public final class Render {
    public static final int inner_shift = 1;

    public static void drawRectangle(@NotNull Graphics2D g2d, @NotNull DiscreteMapPosition position, Color innerColor) {
        // Border
        int x_pos = position.getXPixel();
        int y_pos = position.getYPixel();

        g2d.setColor(Colors.borderColor);
        g2d.fillRect(x_pos, y_pos, DiscreteMap.tileSize, DiscreteMap.tileSize);

        // Inner fill
        int x_innerPos = x_pos + inner_shift;
        int y_innerPos = y_pos + inner_shift;
        int innerTileSize = DiscreteMap.tileSize - 2 * inner_shift;

        g2d.setColor(innerColor);
        g2d.fillRect(x_innerPos, y_innerPos, innerTileSize, innerTileSize);
    }
}
