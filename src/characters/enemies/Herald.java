package characters.enemies;

import main.GamePanel;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class Herald extends Enemy {
    public Herald(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Override
    protected void receiveDamage(
            int damageAmount,
            @NotNull Supplier<Integer> defenseSupplier
    ) {
        int defenseAmount = defenseSupplier.get();

        int damageDealt = damageAmount / defenseAmount;
        if (damageDealt == 0) {return;}

        if (getHitPoints() <= damageDealt) {
            setHitPoints(0);
            return;
        }

        setHitPoints(getHitPoints() - damageDealt);
    }
}
