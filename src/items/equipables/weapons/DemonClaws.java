package items.equipables.weapons;

import map.DiscreteMapPosition;

import java.util.List;

public final class DemonClaws extends Weapon {
    public DemonClaws(DiscreteMapPosition position) {
        super(
                position,
                List.of(),
                List.of(
                        new Damage(Damage.DamageType.SWING, new Damage.Dice("2d6+4")),
                        new Damage(Damage.DamageType.MAGICAL, new Damage.Dice("2d6+2"))
                ),
                "Demon Claws"
        );
    }

}
