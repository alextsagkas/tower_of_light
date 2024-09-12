package interfaces;

import map.DiscreteMapPosition;

import java.awt.*;


public interface Drawable {
    void draw(Graphics2D g2d);

    DiscreteMapPosition getPosition();

    void setCollision(boolean collision);

    boolean getCollision();
}
