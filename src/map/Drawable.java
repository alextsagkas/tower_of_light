package map;

import java.awt.*;


public interface Drawable {
    void draw(Graphics2D g2d);

    void setPosition(DiscreteMapPosition pos);

    DiscreteMapPosition getPosition();

    void setCollision(boolean collision);

    boolean getCollision();
}
