package characters.enemies;

import items.equipables.weapons.Dagger;
import items.equipables.weapons.Weapon;
import main.GamePanel;

import java.util.ArrayList;

public final class Vampire extends Enemy {
    public Vampire(GamePanel gamePanel) {
        super(gamePanel);
    }

    protected String initializeName() {
        return "Vampire";
    }

    protected Integer initializeMaxHitPoints() {
        return 20;
    }

    protected Weapon initializeWeapon() {
        return new Dagger(getPosition());
    }

    protected Integer initializeSwingDefense() {
        return 0;
    }

    protected Integer initializeThrustDefense() {
        return 0;
    }

    protected Integer initializeMagicalDefense() {
        return Integer.MAX_VALUE;
    }

    protected Integer initializeExperiencePoints() {
        return 30;
    }

    ArrayList<Integer> initializeAppearOnLevel() {
        ArrayList<Integer> appearOnLevel = new ArrayList<>();
        appearOnLevel.add(1);
        appearOnLevel.add(2);
        return appearOnLevel;
    }

    protected Enemy deepCopy() {
        return new Vampire(gamePanel);
    }
}

