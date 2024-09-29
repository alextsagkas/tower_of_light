package items;

import characters.player.Player;
import items.effects.ItemEffect;
import items.effects.ItemEffect.ItemEffectType;
import items.equipables.EquipableItem;
import items.equipables.weapons.Damage;
import items.equipables.weapons.Weapon;
import items.usables.HealthPotion;
import items.usables.ManaPotion;
import items.usables.UsableItem;
import map.DiscreteMapPosition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Generate random items.
 */
public final class ItemsGenerator {
    private enum Rarity {
        NONE(0),
        FEEBLE(1),
        COMMON(4),
        RARE(8),
        SUPREME(12);

        private final int totalBonus;

        Rarity(int totalBonus) {
            this.totalBonus = totalBonus;
        }

        public int getTotalBonus() {
            return totalBonus;
        }
    }

    private static final String[] namePrefix = {
            "laughing",
            "deadly",
            "eternal",
            "amphibious",
            "improbable",
            "reckless",
            "smiting",
            "boring",
            "mysterious"
    };

    private final static Map<Player.SlotType, List<String>> slotsMap = Map.of(
            Player.SlotType.MAIN_HAND,
            List.of(
                    "Sword",
                    "Katana",
                    "Staff",
                    "Axe",
                    "Mace",
                    "Halberd",
                    "Cutlass",
                    "Morningstar"
            ),
            Player.SlotType.OFF_HAND,
            List.of(
                    "Short Sword",
                    "Dagger",
                    "Baton",
                    "Spiked Skull"
            ),
            Player.SlotType.TRINKET,
            List.of(
                    "Ring",
                    "Necklace",
                    "Crown"
            )
    );

    private final static String[] nameSuffix = {
            "of Doom",
            "of Pestilence",
            "of the Dead",
            "of Mythos",
            "of the clan McCloud",
            "of the Stars"
    };

    /**
     * Generate random name for predefined prefixes, slots and suffixed.
     *
     * @param rarity   how rare is the item.
     * @param slotType the slot on which it will be equipped.
     * @return the random name.
     */
    private static @NotNull String nameGenerator(Rarity rarity, Player.SlotType slotType) {
        Random rng = new Random();

        String rarityName;
        if (rarity == Rarity.NONE) {
            rarityName = "";
        } else {
            rarityName = rarity.toString().toLowerCase() + " ";
        }

        List<String> slotNames = slotsMap.get(slotType);
        String slotName = slotNames.get(rng.nextInt(slotNames.size()));

        return rarityName +
               namePrefix[rng.nextInt(namePrefix.length)] +
               " " +
               slotName +
               " " +
               nameSuffix[rng.nextInt(nameSuffix.length)];
    }

    /**
     * Return a random usable item from the ones already implemented.
     *
     * @param position the position of the usable item.
     * @return the random usable item.
     */
    public static UsableItem randomUsableItem(DiscreteMapPosition position) {
        Random rng = new Random();

        UsableItem[] itemProbabilities = {
                new HealthPotion(position), new HealthPotion(position), new HealthPotion(position),
                new ManaPotion(position), new ManaPotion(position)
        };

        return itemProbabilities[rng.nextInt(itemProbabilities.length)];
    }

    /**
     * Return a random equipable item from scratch.
     *
     * @param position the position of the equipable item.
     * @return the random equipable item.
     */
    @Contract("_ -> new")
    public static @NotNull EquipableItem randomEquipableItem(DiscreteMapPosition position) {
        Random rng = new Random();

        Rarity[] rarityProbabilities = {
                Rarity.NONE, Rarity.NONE, Rarity.NONE, Rarity.NONE, Rarity.NONE,
                Rarity.FEEBLE, Rarity.FEEBLE, Rarity.FEEBLE, Rarity.FEEBLE, Rarity.FEEBLE,
                Rarity.COMMON, Rarity.COMMON, Rarity.COMMON, Rarity.COMMON,
                Rarity.COMMON, Rarity.COMMON, Rarity.COMMON,
                Rarity.RARE, Rarity.RARE, Rarity.RARE,
                Rarity.SUPREME
        };

        Rarity rarity = rarityProbabilities[rng.nextInt(rarityProbabilities.length)];
        Player.SlotType slotType = Player.SlotType.values()[rng.nextInt(Player.SlotType.values().length)];
        String itemName = nameGenerator(rarity, slotType);

        int[] bonusNumbers = {0, 0, 0, 0}; // HP_BOOST, MP_BOOST, STR_BOOST, INT_BOOST
        ItemEffectType[] bonusItemEffectTypes = {
                ItemEffectType.HP_BOOST,
                ItemEffectType.MP_BOOST,
                ItemEffectType.STR_BOOST,
                ItemEffectType.INT_BOOST,

        };
        Integer[] damageAmounts = {0, 0, 0}; // SWING, THRUST, MAGICAL
        Damage.DamageType[] damageTypes = {
                Damage.DamageType.SWING,
                Damage.DamageType.THRUST,
                Damage.DamageType.MAGICAL
        };
        for (int totalBonus = rarity.getTotalBonus(); totalBonus > 0; totalBonus--) {
            bonusNumbers[rng.nextInt(bonusNumbers.length)]++;
            damageAmounts[rng.nextInt(damageAmounts.length)]++;
        }

        List<ItemEffect> itemEffects = new ArrayList<>();
        for (int i = 0; i < bonusNumbers.length; i++) {
            if (bonusNumbers[i] > 0) {
                itemEffects.add(new ItemEffect(bonusItemEffectTypes[i], bonusNumbers[i]));
            }
        }

        List<Damage> damages = new ArrayList<>();
        for (int i = 0; i < damageAmounts.length; i++) {
            if (damageAmounts[i] > 0) {
                damages.add(
                        new Damage(
                                damageTypes[i],
                                new Damage.Dice(bonusNumbers[i], 6, 2)
                        )
                );
            }
        }

        boolean isWeapon = slotType == Player.SlotType.MAIN_HAND || slotType == Player.SlotType.OFF_HAND;
        if (isWeapon) {
            return new Weapon(position, itemEffects, damages, itemName);
        } else {
            return new EquipableItem(position, itemEffects, itemName);
        }
    }

    private ItemsGenerator() {}
}
