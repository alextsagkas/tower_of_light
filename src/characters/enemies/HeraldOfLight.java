package characters.enemies;

import items.equipables.weapons.LightBringer;
import items.equipables.weapons.Weapon;
import main.GamePanel;

import java.util.ArrayList;

public final class HeraldOfLight extends Herald {
    public HeraldOfLight(GamePanel gamePanel) {
        super(gamePanel);
    }

    protected String initializeName() {
        return "Herald of Light";
    }

    protected Integer initializeMaxHitPoints() {
        return 160;
    }

    protected Weapon initializeWeapon() {
        return new LightBringer(getPosition());
    }

    protected Integer initializeSwingDefense() {
        return 2;
    }

    protected Integer initializeThrustDefense() {
        return 2;
    }

    protected Integer initializeMagicalDefense() {
        return 2;
    }

    protected Integer initializeExperiencePoints() {
        return 400;
    }

    ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(6);
        return appearOnLevel;
    }

    protected Enemy deepCopy() {
        return new HeraldOfLight(gamePanel);
    }
}
