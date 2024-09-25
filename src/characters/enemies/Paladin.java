package characters.enemies;

import items.equipables.weapons.HammerOfWrath;
import items.equipables.weapons.Weapon;
import main.GamePanel;

import java.util.ArrayList;

public final class Paladin extends Enemy {
    public Paladin(GamePanel gamePanel) {
        super(gamePanel);
    }

    protected String initializeName() {
        return "Paladin";
    }

    protected Integer initializeMaxHitPoints() {
        return 100;
    }

    protected Weapon initializeWeapon() {
        return new HammerOfWrath(getPosition());
    }

    protected Integer initializeSwingDefense() {
        return 5;
    }

    protected Integer initializeThrustDefense() {
        return 3;
    }

    protected Integer initializeMagicalDefense() {
        return 2;
    }

    protected Integer initializeExperiencePoints() {
        return 80;
    }

    ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(3);
        appearOnLevel.add(4);
        appearOnLevel.add(5);
        return appearOnLevel;
    }

    protected Enemy deepCopy() {
        return new Paladin(gamePanel);
    }
}
