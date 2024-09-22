package items.equipables;

import items.effects.ItemEffect;
import map.DiscreteMapPosition;

import java.util.List;

public final class Weapon extends EquipableItem {
    public Weapon(
            DiscreteMapPosition discreteMapPosition,
            List<ItemEffect> itemEffects,
            String itemName
    ) {
        super(discreteMapPosition, itemEffects, itemName);
    }
}
