package characters.player;

import org.jetbrains.annotations.NotNull;

public final class Paladin extends Warrior {
    @Override
    protected void updateStats(@NotNull Player player) {
        switch (player.getLevel()) {
            case 1:
                player.setMaxHitPoints(player.getMaxHitPoints() + 10);
                player.setMaxManaPoints(player.getMaxManaPoints() + 10);
                player.setStrength(player.getStrength() + 5);
                player.setIntellect(player.getIntellect() + 5);
                break;
            case 2:
                player.setMaxHitPoints(player.getMaxHitPoints() + 13);
                player.setMaxManaPoints(player.getMaxManaPoints() + 20);
                player.setStrength(player.getStrength() + 7);
                player.setIntellect(player.getIntellect() + 9);
                break;
            case 3:
                player.setMaxHitPoints(player.getMaxHitPoints() + 16);
                player.setMaxManaPoints(player.getMaxManaPoints() + 30);
                player.setStrength(player.getStrength() + 9);
                player.setIntellect(player.getIntellect() + 13);
                break;
            case 4:
                player.setMaxHitPoints(player.getMaxHitPoints() + 20);
                player.setMaxManaPoints(player.getMaxManaPoints() + 40);
                player.setStrength(player.getStrength() + 11);
                player.setIntellect(player.getIntellect() + 17);
                break;
            case 5:
                player.setMaxHitPoints(player.getMaxHitPoints() + 28);
                player.setMaxManaPoints(player.getMaxManaPoints() + 50);
                player.setStrength(player.getStrength() + 13);
                player.setIntellect(player.getIntellect() + 21);
                break;
            case 6:
                player.setMaxHitPoints(player.getMaxHitPoints() + 40);
                player.setMaxManaPoints(player.getMaxManaPoints() + 700);
                player.setStrength(player.getStrength() + 15);
                player.setIntellect(player.getIntellect() + 25);
                break;
        }
    }

    @Override
    protected void assignWarrior() {
        setWarrior("Paladin");
    }
}
