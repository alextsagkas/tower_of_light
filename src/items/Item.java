package items;

import map.DiscreteMapPosition;
import ui.Colors;

import java.awt.*;

public abstract class Item {
    private DiscreteMapPosition position;

    private Color invisibleColor;
    private Color visibleColor;

    private boolean discovered;
    private boolean visible;

    public Item(DiscreteMapPosition position) {
        this.position = position;
        this.discovered = false;
        this.visible = false;
    }

    public void setPosition(DiscreteMapPosition position) {
        this.position = position;
    }

    public DiscreteMapPosition getPosition() {
        return position;
    }

    public void setInvisibleColor(Color invisibleColor) {
        this.invisibleColor = invisibleColor;
    }

    public void setVisibleColor(Color visibleColor) {
        this.visibleColor = visibleColor;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    protected abstract void drawOnCorner(Graphics2D g2d, DiscreteMapPosition position, Color color);

    private void drawUndiscovered(Graphics2D g2d) {
        drawOnCorner(g2d, getPosition(), Colors.undiscoveredColor);
    }

    private void drawInvisible(Graphics2D g2d) {
        drawOnCorner(g2d, getPosition(), invisibleColor);
    }

    private void drawVisible(Graphics2D g2d) {
        drawOnCorner(g2d, getPosition(), visibleColor);
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
}
