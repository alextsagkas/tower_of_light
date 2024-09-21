package characters.player;

public final class Knight extends Warrior {
    @Override
    protected void updateStats(Player player) {
        switch (player.getLevel()) {
            case 1:
                player.setMaxHitPoints(player.getMaxHitPoints() + 10);
                player.setStrength(player.getStrength() + 2);
                break;
            case 2:
                player.setMaxHitPoints(player.getMaxHitPoints() + 15);
                player.setStrength(player.getStrength() + 4);
                break;
            case 3:
                player.setMaxHitPoints(player.getMaxHitPoints() + 20);
                player.setStrength(player.getStrength() + 6);
                break;
            case 4:
                player.setMaxHitPoints(player.getMaxHitPoints() + 25);
                player.setStrength(player.getStrength() + 8);
                break;
            case 5:
                player.setMaxHitPoints(player.getMaxHitPoints() + 30);
                player.setStrength(player.getStrength() + 10);
                break;
            case 6:
                player.setMaxHitPoints(player.getMaxHitPoints() + 40);
                player.setStrength(player.getStrength() + 12);
                break;
        }
    }

    @Override
    protected void assignWarrior() {
        setWarrior("Knight");
    }

}
