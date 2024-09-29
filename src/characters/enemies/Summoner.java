package characters.enemies;

import items.equipables.weapons.SummoningStaff;
import items.equipables.weapons.Weapon;
import main.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class Summoner extends Enemy {
    public Summoner(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Contract(pure = true)
    protected @NotNull String initializeName() {
        return "Summoner";
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMaxHitPoints() {
        return 40;
    }

    @Contract(" -> new")
    protected @NotNull Weapon initializeWeapon() {
        return new SummoningStaff(getPosition());
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
        return new Summoner(gamePanel);
    }
}
