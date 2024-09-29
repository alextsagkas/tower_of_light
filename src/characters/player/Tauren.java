package characters.player;

import org.jetbrains.annotations.NotNull;

public final class Tauren extends Race {
    @Override
    protected void initializeStats(@NotNull Player player) {
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
