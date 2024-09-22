package items;

import characters.player.Player;
import items.effects.ItemEffect;
import items.effects.ItemEffect.ItemEffectType;
import items.equipables.EquipableItem;
import items.equipables.Weapon;
import map.DiscreteMapPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class ItemsGenerator {
    public enum Rarity {
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

    static String[] namePrefix = {
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

    static Map<Player.SlotType, List<String>> slotsMap = Map.of(
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

    static String[] nameSuffix = {
            "of Doom",
            "of Pestilence",
            "of the Dead",
            "of Mythos",
            "of the clan McCloud",
            "of the Stars"
    };

    public static String nameGenerator(Rarity rarity, Player.SlotType slotType) {
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

    public static EquipableItem randomEquipableItem(DiscreteMapPosition position) {
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
        for (int totalBonus = rarity.getTotalBonus(); totalBonus > 0; totalBonus--) {
            bonusNumbers[rng.nextInt(bonusNumbers.length)]++;
        }

        List<ItemEffect> itemEffects = new ArrayList<>();
        for (int i = 0; i < bonusNumbers.length; i++) {
            if (bonusNumbers[i] > 0) {
                itemEffects.add(new ItemEffect(bonusItemEffectTypes[i], bonusNumbers[i]));
            }
        }

        boolean isWeapon = slotType == Player.SlotType.MAIN_HAND || slotType == Player.SlotType.OFF_HAND;
        if (isWeapon) {
            return new Weapon(position, itemEffects, itemName);
        } else {
            return new EquipableItem(position, itemEffects, itemName);
        }
    }
}
