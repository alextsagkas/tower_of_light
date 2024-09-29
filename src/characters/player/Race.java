package characters.player;

import org.jetbrains.annotations.NotNull;

/**
 * Delegate race-specific functionality of player to this class. Extend it
 * to provide the initialization of player's stats.
 */
abstract public class Race {
    private String race;

    public Race() {
        assignRace();
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    abstract protected void initializeStats(Player player);

    abstract protected void assignRace();

    public static @NotNull Race parseRace(@NotNull String race) throws IllegalArgumentException {
        return switch (race) {
            case "Elf" -> new Elf();
            case "Human" -> new Human();
            case "Orc" -> new Orc();
            case "Tauren" -> new Tauren();
            default -> throw new IllegalArgumentException(String.format("%s is an invalid race", race));
        };
    }
}
