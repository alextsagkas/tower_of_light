package items.usables;

import items.effects.ItemEffect;
import map.DiscreteMapPosition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    protected @NotNull List<ItemEffect> initializeItemEffects() {
        List<ItemEffect> ItemEffects = new ArrayList<>();
        ItemEffects.add(new ItemEffect(ItemEffect.ItemEffectType.HP_REPLENISH, 30));
        return ItemEffects;
    }

    @Contract(pure = true)
    @Override
    protected @NotNull String initializeItemName() {
        return "Health Potion";
    }
}
