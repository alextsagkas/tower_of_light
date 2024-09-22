package items.usables;

import items.effects.ItemEffect;
import map.DiscreteMapPosition;

import java.util.ArrayList;
import java.util.List;

public final class HealthPotion extends UsableItem {
    public HealthPotion(DiscreteMapPosition discreteMapPosition) {
        super(discreteMapPosition);
    }

    @Override
    protected int initializeUsesRemaining() {
        return 3;
    }

    @Override
    protected List<ItemEffect> initializeItemEffects() {
        List<ItemEffect> ItemEffects = new ArrayList<>();
        ItemEffects.add(new ItemEffect(ItemEffect.ItemEffectType.HP_REPLENISH, 30));
        return ItemEffects;
    }

    @Override
    protected String initializeItemName() {
        return "Health Potion";
    }
}
