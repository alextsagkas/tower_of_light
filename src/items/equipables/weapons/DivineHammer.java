package items.equipables.weapons;

import map.DiscreteMapPosition;

import java.util.List;

public final class DivineHammer extends Weapon {
    public DivineHammer(DiscreteMapPosition position) {
        super(
                position,
                List.of(),
                List.of(
                        new Damage(Damage.DamageType.THRUST, new Damage.Dice("2d6+4")),
                        new Damage(Damage.DamageType.MAGICAL, new Damage.Dice("2d6+2"))
                ),
                "Divine Hammer"
        );
    }

}
