package characters;

import org.jetbrains.annotations.NotNull;

public enum Direction {
    UP, DOWN, LEFT, RIGHT, NONE;

    @Override
    public @NotNull String toString() {
        return "Direction{" + this.name() + "}";
    }
}
