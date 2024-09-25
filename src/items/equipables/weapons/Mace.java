package items.equipables.weapons;

import map.DiscreteMapPosition;

import java.util.List;

public final class Mace extends Weapon {
    public Mace(DiscreteMapPosition position) {
        super(
                position,
                List.of(),
                List.of(new Damage(Damage.DamageType.SWING, new Damage.Dice("1d6+1"))),
                "Mace"
        );
    }
}
