package items.equipables.weapons;

import map.DiscreteMapPosition;

import java.util.List;

public final class Staff extends Weapon {
    public Staff(DiscreteMapPosition position) {
        super(
                position,
                List.of(),
                List.of(
                        new Damage(Damage.DamageType.SWING, new Damage.Dice("2d6+2")),
                        new Damage(Damage.DamageType.MAGICAL, new Damage.Dice("1d6+1"))
                ),
                "Staff"
        );
    }

}
