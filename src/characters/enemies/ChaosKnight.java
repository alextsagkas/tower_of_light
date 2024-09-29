package characters.enemies;

import items.equipables.weapons.SwordOfChaos;
import items.equipables.weapons.Weapon;
import main.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class ChaosKnight extends Enemy {
    public ChaosKnight(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Contract(pure = true)
    protected @NotNull String initializeName() {
        return "Chaos Knight";
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMaxHitPoints() {
        return 30;
    }

    @Contract(" -> new")
    protected @NotNull Weapon initializeWeapon() {
        return new SwordOfChaos(getPosition());
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeSwingDefense() {
        return 3;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeThrustDefense() {
        return 3;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMagicalDefense() {
        return 1;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeExperiencePoints() {
        return 50;
    }

    @NotNull ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(2);
        appearOnLevel.add(3);
        appearOnLevel.add(4);
        return appearOnLevel;
    }

    @Contract(" -> new")
    protected @NotNull Enemy deepCopy() {
        return new ChaosKnight(gamePanel);
    }
}
