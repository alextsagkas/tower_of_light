package characters.enemies;

import items.equipables.weapons.Staff;
import items.equipables.weapons.Weapon;
import main.GamePanel;

import java.util.ArrayList;

public final class Bishop extends Enemy {
    public Bishop(GamePanel gamePanel) {
        super(gamePanel);
    }

    protected String initializeName() {
        return "Bishop";
    }

    protected Integer initializeMaxHitPoints() {
        return 40;
    }

    protected Weapon initializeWeapon() {
        return new Staff(getPosition());
    }

    protected Integer initializeSwingDefense() {
        return 0;
    }

    protected Integer initializeThrustDefense() {
        return 0;
    }

    protected Integer initializeMagicalDefense() {
        return 5;
    }

    protected Integer initializeExperiencePoints() {
        return 60;
    }

    ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(2);
        appearOnLevel.add(3);
        appearOnLevel.add(4);
        return appearOnLevel;
    }

    protected Enemy deepCopy() {
        return new Bishop(gamePanel);
    }
}
