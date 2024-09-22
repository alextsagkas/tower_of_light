package items.effects;

public final class ItemEffect {
    private final ItemEffectType itemEffectType;
    private final int statEnhancement;

    public ItemEffect(ItemEffectType itemEffectType, int statEnhancement) {
        this.itemEffectType = itemEffectType;
        this.statEnhancement = statEnhancement;
    }

    public int getStatEnhancement() {
        return statEnhancement;
    }

    public ItemEffectType getItemEffectType() {
        return itemEffectType;
    }

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

    @Override
    public String toString() {
        return "ItemEffect{" +
               "itemEffectType=" + itemEffectType +
               ", statEnhancement=" + statEnhancement +
               '}';
    }
}
