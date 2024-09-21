package characters.player;

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

    public static Race parseRace(String race) throws IllegalArgumentException {
        return switch (race) {
            case "Elf" -> new Elf();
            case "Human" -> new Human();
            case "Orc" -> new Orc();
            case "Tauren" -> new Tauren();
            default -> throw new IllegalArgumentException(String.format("%s is an invalid race", race));
        };
    }
}
