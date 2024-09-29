package items.usables;

import items.Item;
import items.effects.ItemEffect;
import map.DiscreteMapPosition;
import ui.Colors;
import ui.Render;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Aggregate usable items functionalities. Provide a use() method to consume the
 * item. Allow subclassing to extend and complete the class.
 */
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
                          .map(ItemEffect::itemEffectType)
                          .distinct()
                          .collect(Collectors.toList());
    }

    /**
     * Get the ItemEffects list of the usable item.
     *
     * @return the ItemEffects list that will be applied to the entity upon the item's use.
     */
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
