package characters.enemies;

import items.equipables.weapons.HammerOfWrath;
import items.equipables.weapons.Weapon;
import main.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class Paladin extends Enemy {
    public Paladin(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Contract(pure = true)
    protected @NotNull String initializeName() {
        return "Paladin";
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMaxHitPoints() {
        return 100;
    }

    @Contract(" -> new")
    protected @NotNull Weapon initializeWeapon() {
        return new HammerOfWrath(getPosition());
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeSwingDefense() {
        return 5;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeThrustDefense() {
        return 3;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMagicalDefense() {
        return 2;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeExperiencePoints() {
        return 80;
    }

    @NotNull ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(3);
        appearOnLevel.add(4);
        appearOnLevel.add(5);
        return appearOnLevel;
    }

    @Contract(" -> new")
    protected @NotNull Enemy deepCopy() {
        return new Paladin(gamePanel);
    }
}
