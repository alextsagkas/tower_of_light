package characters.enemies;

import items.equipables.weapons.EbonBlade;
import items.equipables.weapons.Weapon;
import main.GamePanel;

import java.util.ArrayList;

public final class FallenHero extends Enemy {
    public FallenHero(GamePanel gamePanel) {
        super(gamePanel);
    }

    protected String initializeName() {
        return "Fallen Hero";
    }

    protected Integer initializeMaxHitPoints() {
        return 100;
    }

    protected Weapon initializeWeapon() {
        return new EbonBlade(getPosition());
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
        return new FallenHero(gamePanel);
    }
}
