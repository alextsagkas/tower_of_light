package items.equipables.weapons;

import map.DiscreteMapPosition;

import java.util.List;

public final class Dagger extends Weapon {
    public Dagger(DiscreteMapPosition position) {
        super(
                position,
                List.of(),
                List.of(new Damage(Damage.DamageType.THRUST, new Damage.Dice("1d6+1"))),
                "Dagger"
        );
    }
}
