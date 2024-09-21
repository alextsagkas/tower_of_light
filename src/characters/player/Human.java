package characters.player;

public final class Human extends Race {
    @Override
    protected void initializeStats(Player player) {
        player.setStrength(9);
        player.setIntellect(9);
        player.setSwingDefence(1);
        player.setThrustDefence(1);
        player.setMagicalDefence(1);
    }

    @Override
    protected void assignRace() {
        setRace("Human");
    }
}
