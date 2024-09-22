package items.equipables;

import items.Item;
import items.effects.ItemEffect;
import map.DiscreteMapPosition;
import ui.Colors;
import ui.Render;

import java.awt.*;
import java.util.List;

public class EquipableItem extends Item {
    private final List<ItemEffect> itemEffects;
    private final String itemName;

    public EquipableItem(
            DiscreteMapPosition discreteMapPosition,
            List<ItemEffect> itemEffects,
            String itemName
    ) {
        super(discreteMapPosition);

        setInvisibleColor(Colors.equipableItemInvisibleColor);
        setVisibleColor(Colors.equipableItemVisibleColor);

        this.itemEffects = itemEffects;
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public List<ItemEffect> getItemEffects() {
        return itemEffects;
    }

    public List<ItemEffect> getUndoItemEffects() {
        return itemEffects.stream()
                          .map(itemEffect -> new ItemEffect(
                                  itemEffect.getItemEffectType(),
                                  -itemEffect.getStatEnhancement()
                          ))
                          .toList();
    }

    @Override
    protected void drawOnCorner(Graphics2D g2d, DiscreteMapPosition position, Color color) {
        Render.drawInnerBottomRightCorner(g2d, position, color);
    }

    @Override
    public String toString() {
        return "EquipableItem{" +
               "itemEffects=" + itemEffects +
               ", itemName='" + itemName + '\'' +
               '}';
    }
}
