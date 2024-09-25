package characters.enemies;

import items.equipables.weapons.SummoningStaff;
import items.equipables.weapons.Weapon;
import main.GamePanel;

import java.util.ArrayList;

public final class Summoner extends Enemy {
    public Summoner(GamePanel gamePanel) {
        super(gamePanel);
    }

    protected String initializeName() {
        return "Summoner";
    }

    protected Integer initializeMaxHitPoints() {
        return 40;
    }

    protected Weapon initializeWeapon() {
        return new SummoningStaff(getPosition());
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
        return new Summoner(gamePanel);
    }
}
