package characters.player;

import org.jetbrains.annotations.NotNull;

/**
 * Delegate the warrior-specific characteristics of player to this class.
 * Extend it to provide a rule for player to update his stats based on his level.
 */
abstract public class Warrior {
    private String warrior;

    public Warrior() {
        assignWarrior();
    }

    public String getWarrior() {
        return warrior;
    }

    public void setWarrior(String warrior) {
        this.warrior = warrior;
    }

    abstract protected void updateStats(Player player);

    abstract protected void assignWarrior();

    public static @NotNull Warrior parseWarrior(@NotNull String warrior) throws IllegalArgumentException {
        return switch (warrior) {
            case "Knight" -> new Knight();
            case "Mage" -> new Mage();
            case "Paladin" -> new Paladin();
            default -> throw new IllegalArgumentException(String.format("%s is an invalid warrior", warrior));
        };

    }
}
