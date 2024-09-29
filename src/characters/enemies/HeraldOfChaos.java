package characters.enemies;

import items.equipables.weapons.EdgeOfChaos;
import items.equipables.weapons.Weapon;
import main.GamePanel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class HeraldOfChaos extends Herald {
    public HeraldOfChaos(GamePanel gamePanel) {
        super(gamePanel);
    }

    @Contract(pure = true)
    protected @NotNull String initializeName() {
        return "Herald of Chaos";
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMaxHitPoints() {
        return 160;
    }

    @Contract(" -> new")
    protected @NotNull Weapon initializeWeapon() {
        return new EdgeOfChaos(getPosition());
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeSwingDefense() {
        return 2;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeThrustDefense() {
        return 2;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeMagicalDefense() {
        return 2;
    }

    @Contract(pure = true)
    protected @NotNull Integer initializeExperiencePoints() {
        return 400;
    }

    @NotNull ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(6);
        return appearOnLevel;
    }

    @Contract(" -> new")
    protected @NotNull Enemy deepCopy() {
        return new HeraldOfChaos(gamePanel);
    }
}
