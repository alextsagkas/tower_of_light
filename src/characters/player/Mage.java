package characters.player;

public final class Mage extends Warrior {
    @Override
    protected void updateStats(Player player) {
        switch (player.getLevel()) {
            case 1:
                player.setMaxHitPoints(player.getMaxHitPoints() + 8);
                player.setMaxManaPoints(player.getMaxManaPoints() + 20);
                player.setStrength(player.getStrength() + 2);
                player.setIntellect(player.getIntellect() + 8);
                break;
            case 2:
                player.setMaxHitPoints(player.getMaxHitPoints() + 12);
                player.setMaxManaPoints(player.getMaxManaPoints() + 40);
                player.setStrength(player.getStrength() + 3);
                player.setIntellect(player.getIntellect() + 16);
                break;
            case 3:
                player.setMaxHitPoints(player.getMaxHitPoints() + 16);
                player.setMaxManaPoints(player.getMaxManaPoints() + 60);
                player.setStrength(player.getStrength() + 4);
                player.setIntellect(player.getIntellect() + 24);
                break;
            case 4:
                player.setMaxHitPoints(player.getMaxHitPoints() + 20);
                player.setMaxManaPoints(player.getMaxManaPoints() + 80);
                player.setStrength(player.getStrength() + 5);
                player.setIntellect(player.getIntellect() + 32);
                break;
            case 5:
                player.setMaxHitPoints(player.getMaxHitPoints() + 24);
                player.setMaxManaPoints(player.getMaxManaPoints() + 100);
                player.setStrength(player.getStrength() + 6);
                player.setIntellect(player.getIntellect() + 40);
                break;
            case 6:
                player.setMaxHitPoints(player.getMaxHitPoints() + 30);
                player.setMaxManaPoints(player.getMaxManaPoints() + 120);
                player.setStrength(player.getStrength() + 7);
                player.setIntellect(player.getIntellect() + 48);
                break;
        }
    }

    @Override
    protected void assignWarrior() {
        setWarrior("Mage");
    }
}
