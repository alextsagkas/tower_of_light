package characters.player;

import org.jetbrains.annotations.NotNull;

public final class Human extends Race {
    @Override
    protected void initializeStats(@NotNull Player player) {
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
