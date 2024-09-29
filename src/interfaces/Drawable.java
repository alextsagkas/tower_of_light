package interfaces;

import java.awt.*;


/**
 * Tagging interface to show that a class is drawable on the game panel.
 */
public interface Drawable {
    /**
     * Present the component on the game panel.
     *
     * @param g2d the graphic to draw the component on.
     */
    void draw(Graphics2D g2d);
}
