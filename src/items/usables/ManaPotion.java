package items.usables;

import items.effects.ItemEffect;
import map.DiscreteMapPosition;

import java.util.ArrayList;
import java.util.List;

public final class ManaPotion extends UsableItem {
    public ManaPotion(DiscreteMapPosition discreteMapPosition) {
        super(discreteMapPosition);
    }

    @Override
    protected int initializeUsesRemaining() {
        return 3;
    }

    @Override
    protected List<ItemEffect> initializeItemEffects() {
        List<ItemEffect> ItemEffects = new ArrayList<>();
        ItemEffects.add(new ItemEffect(ItemEffect.ItemEffectType.MP_REPLENISH, 40));
        return ItemEffects;
    }

    @Override
    protected String initializeItemName() {
        return "Mana Potion";
    }
}
