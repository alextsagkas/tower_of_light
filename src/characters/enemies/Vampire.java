package characters.enemies;

import items.equipables.weapons.Dagger;
import items.equipables.weapons.Weapon;
import main.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class Vampire extends Enemy {
    public Vampire(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Contract(pure = true)
    protected @NotNull String initializeName() {
        return "Vampire";
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMaxHitPoints() {
        return 20;
    }

    @Contract(" -> new")
    protected @NotNull Weapon initializeWeapon() {
        return new Dagger(getPosition());
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeSwingDefense() {
        return 0;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeThrustDefense() {
        return 0;
    }

    protected Integer initializeMagicalDefense() {
        return Integer.MAX_VALUE;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeExperiencePoints() {
        return 30;
    }

    @NotNull ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(1);
        appearOnLevel.add(2);
        return appearOnLevel;
    }

    @Contract(" -> new")
    protected @NotNull Enemy deepCopy() {
        return new Vampire(gamePanel);
    }
}

