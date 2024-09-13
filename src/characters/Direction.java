package characters;

public enum Direction {
    UP, DOWN, LEFT, RIGHT, NONE;

    @Override
    public String toString() {
        return "Direction{" + this.name() + "}";
    }
}
