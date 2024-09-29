package characters.player;

import org.jetbrains.annotations.NotNull;

public final class Elf extends Race {
    @Override
    protected void initializeStats(@NotNull Player player) {
        player.setStrength(6);
        player.setIntellect(12);
        player.setSwingDefence(0);
        player.setThrustDefence(1);
        player.setMagicalDefence(2);
    }

    @Override
    protected void assignRace() {
        setRace("Elf");
    }
}
