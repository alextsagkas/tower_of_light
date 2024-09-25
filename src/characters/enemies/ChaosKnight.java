package characters.enemies;

import items.equipables.weapons.SwordOfChaos;
import items.equipables.weapons.Weapon;
import main.GamePanel;

import java.util.ArrayList;

public final class ChaosKnight extends Enemy {
    public ChaosKnight(GamePanel gamePanel) {
        super(gamePanel);
    }

    protected String initializeName() {
        return "Chaos Knight";
    }

    protected Integer initializeMaxHitPoints() {
        return 30;
    }

    protected Weapon initializeWeapon() {
        return new SwordOfChaos(getPosition());
    }

    protected Integer initializeSwingDefense() {
        return 3;
    }

    protected Integer initializeThrustDefense() {
        return 3;
    }

    protected Integer initializeMagicalDefense() {
        return 1;
    }

    protected Integer initializeExperiencePoints() {
        return 50;
    }

    ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(2);
        appearOnLevel.add(3);
        appearOnLevel.add(4);
        return appearOnLevel;
    }

    protected Enemy deepCopy() {
        return new ChaosKnight(gamePanel);
    }
}
