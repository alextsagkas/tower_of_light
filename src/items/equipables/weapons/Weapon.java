package items.equipables.weapons;

import items.effects.ItemEffect;
import items.equipables.EquipableItem;
import map.DiscreteMapPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrap EquipableItem with additional method to cause return a damage list.
 */
public class Weapon extends EquipableItem {
    private final List<Damage> damageList;

    public Weapon(
            DiscreteMapPosition discreteMapPosition,
            List<ItemEffect> itemEffects,
            List<Damage> damageList,
            String itemName
    ) {
        super(discreteMapPosition, itemEffects, itemName);
        this.damageList = damageList;
    }

    /**
     * Create a deep copy of the inherent list of Damage objects and
     * return it to the caller to avoid accidental side effects on the
     * weapon. The weapon characteristics should remain as is.
     *
     * @return List with the damage objects that the weapon holds.
     */
    public List<Damage> getDamageList() {
        List<Damage> copiedDamageList = new ArrayList<>();
        for (Damage damage : this.damageList) {
            copiedDamageList.add(new Damage(damage));
        }
        return copiedDamageList;
    }

    @Override
    public String toString() {
        return "Weapon{" + "itemEffects=" +
               getItemEffects() +
               ", itemName=\"" +
               getItemName() +
               '\"' +
               ", damageList=" +
               getDamageList() +
               '}';
    }
}
