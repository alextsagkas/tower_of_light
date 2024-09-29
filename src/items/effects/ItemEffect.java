package items.effects;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Effects items have on entity.
 *
 * @param itemEffectType  the effect enum.
 * @param statEnhancement the enhancement it causes to the entity that equips it.
 */
public record ItemEffect(items.effects.ItemEffect.ItemEffectType itemEffectType, int statEnhancement) {

    public enum ItemEffectType {
        HP_REPLENISH("health replenish"),
        MP_REPLENISH("mana replenish"),
        HP_BOOST("health boost"),
        MP_BOOST("mana boost"),
        STR_BOOST("strength boost"),
        INT_BOOST("intellect boost");

        private final String humanReadableName;

        ItemEffectType(String humanReadableName) {
            this.humanReadableName = humanReadableName;
        }

        public String getHumanReadableName() {
            return humanReadableName;
        }
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "ItemEffect{" +
               "itemEffectType=" + itemEffectType +
               ", statEnhancement=" + statEnhancement +
               '}';
    }
}
