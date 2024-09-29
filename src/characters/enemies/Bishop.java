package characters.enemies;

import items.equipables.weapons.Staff;
import items.equipables.weapons.Weapon;
import main.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class Bishop extends Enemy {
    public Bishop(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Contract(pure = true)
    protected @NotNull String initializeName() {
        return "Bishop";
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMaxHitPoints() {
        return 40;
    }

    @Contract(" -> new")
    protected @NotNull Weapon initializeWeapon() {
        return new Staff(getPosition());
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeSwingDefense() {
        return 0;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeThrustDefense() {
        return 0;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMagicalDefense() {
        return 5;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeExperiencePoints() {
        return 60;
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
        return new Bishop(gamePanel);
    }
}
