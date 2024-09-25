package items.equipables.weapons;

import map.DiscreteMapPosition;

import java.util.List;

public final class BladeOfLight extends Weapon {
    public BladeOfLight(DiscreteMapPosition position) {
        super(
                position,
                List.of(),
                List.of(
                        new Damage(Damage.DamageType.THRUST, new Damage.Dice("2d6+1")),
                        new Damage(Damage.DamageType.MAGICAL, new Damage.Dice("1d3+0"))
                ),
                "Blade of Light"
        );
    }
}
