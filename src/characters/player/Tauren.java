package characters.player;

public final class Tauren extends Race {
    @Override
    protected void initializeStats(Player player) {
        player.setStrength(12);
        player.setIntellect(6);
        player.setSwingDefence(1);
        player.setThrustDefence(2);
        player.setMagicalDefence(0);
    }

    @Override
    protected void assignRace() {
        setRace("Tauren");
    }
}
