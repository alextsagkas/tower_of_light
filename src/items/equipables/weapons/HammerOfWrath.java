package items.equipables.weapons;

import map.DiscreteMapPosition;

import java.util.List;

public final class HammerOfWrath extends Weapon {
    public HammerOfWrath(DiscreteMapPosition position) {
        super(
                position,
                List.of(),
                List.of(
                        new Damage(Damage.DamageType.SWING, new Damage.Dice("2d6+1")),
                        new Damage(Damage.DamageType.MAGICAL, new Damage.Dice("2d6+1"))
                ),
                "Hammer of Wrath"
        );
    }

}
