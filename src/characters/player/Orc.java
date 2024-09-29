package characters.player;

import org.jetbrains.annotations.NotNull;

public final class Orc extends Race {
    @Override
    protected void initializeStats(@NotNull Player player) {
        player.setStrength(10);
        player.setIntellect(8);
        player.setSwingDefence(1);
        player.setThrustDefence(1);
        player.setMagicalDefence(0);
    }

    @Override
    protected void assignRace() {
        setRace("Orc");
    }
}
