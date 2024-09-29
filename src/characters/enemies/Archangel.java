package characters.enemies;

import items.equipables.weapons.DivineHammer;
import items.equipables.weapons.Weapon;
import main.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class Archangel extends Enemy {
    public Archangel(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Contract(pure = true)
    protected @NotNull String initializeName() {
        return "Archangel";
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMaxHitPoints() {
        return 130;
    }

    @Contract(" -> new")
    protected @NotNull Weapon initializeWeapon() {
        return new DivineHammer(getPosition());
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeSwingDefense() {
        return 6;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeThrustDefense() {
        return 6;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMagicalDefense() {
        return 2;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeExperiencePoints() {
        return 200;
    }

    @NotNull ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(5);
        appearOnLevel.add(6);
        return appearOnLevel;
    }

    @Contract(" -> new")
    protected @NotNull Enemy deepCopy() {
        return new Archangel(gamePanel);
    }
}
