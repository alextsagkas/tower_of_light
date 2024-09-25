package characters.enemies;

import items.equipables.weapons.DivineHammer;
import items.equipables.weapons.Weapon;
import main.GamePanel;

import java.util.ArrayList;

public final class Archangel extends Enemy {
    public Archangel(GamePanel gamePanel) {
        super(gamePanel);
    }

    protected String initializeName() {
        return "Archangel";
    }

    protected Integer initializeMaxHitPoints() {
        return 130;
    }

    protected Weapon initializeWeapon() {
        return new DivineHammer(getPosition());
    }

    protected Integer initializeSwingDefense() {
        return 6;
    }

    protected Integer initializeThrustDefense() {
        return 6;
    }

    protected Integer initializeMagicalDefense() {
        return 2;
    }

    protected Integer initializeExperiencePoints() {
        return 200;
    }

    ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(5);
        appearOnLevel.add(6);
        return appearOnLevel;
    }

    protected Enemy deepCopy() {
        return new Archangel(gamePanel);
    }
}
