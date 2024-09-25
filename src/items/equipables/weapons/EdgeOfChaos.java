package items.equipables.weapons;

import items.effects.ItemEffect;
import map.DiscreteMapPosition;

import java.util.List;

public final class EdgeOfChaos extends Weapon {
    public EdgeOfChaos(DiscreteMapPosition position) {
        super(
                position,
                List.of(
                        new ItemEffect(ItemEffect.ItemEffectType.STR_BOOST, 10),
                        new ItemEffect(ItemEffect.ItemEffectType.INT_BOOST, 10),
                        new ItemEffect(ItemEffect.ItemEffectType.MP_BOOST, 10)
                ),
                List.of(
                        new Damage(Damage.DamageType.THRUST, new Damage.Dice("3d6+2")),
                        new Damage(Damage.DamageType.SWING, new Damage.Dice("1d6+2")),
                        new Damage(Damage.DamageType.MAGICAL, new Damage.Dice("0d0+0"))
                ),
                "Edge of Chaos"
        );
    }

}
