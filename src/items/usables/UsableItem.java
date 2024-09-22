package items.usables;

import items.Item;
import items.effects.ItemEffect;
import map.DiscreteMapPosition;
import ui.Colors;
import ui.Render;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class UsableItem extends Item {
    private int usesRemaining;
    private final List<ItemEffect> itemEffects;
    private final String itemName;

    public UsableItem(DiscreteMapPosition discreteMapPosition) {
        super(discreteMapPosition);

        setInvisibleColor(Colors.usableItemInvisibleColor);
        setVisibleColor(Colors.usableItemVisibleColor);

        this.usesRemaining = initializeUsesRemaining();
        this.itemEffects = initializeItemEffects();
        this.itemName = initializeItemName();
    }

    protected abstract int initializeUsesRemaining();

    protected abstract List<ItemEffect> initializeItemEffects();

    protected abstract String initializeItemName();

    public int getUsesRemaining() {return usesRemaining;}

    public String getItemName() {return itemName;}

    private void setUsesRemaining(int usesRemaining) {this.usesRemaining = usesRemaining;}

    public List<ItemEffect.ItemEffectType> discreteItemEffectTypes() {
        return itemEffects.stream()
                          .map(ItemEffect::getItemEffectType)
                          .distinct()
                          .collect(Collectors.toList());
    }

    public List<ItemEffect> use() {
        if (getUsesRemaining() == 0) {
            return List.of();
        } else {
            setUsesRemaining(getUsesRemaining() - 1);
            return itemEffects;
        }
    }

    @Override
    protected void drawOnCorner(Graphics2D g2d, DiscreteMapPosition position, Color color) {
        Render.drawInnerTopLeftCorner(g2d, position, color);
    }
}
