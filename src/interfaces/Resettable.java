package interfaces;

public interface Resettable {
    void reset();

    default void restart() {
        reset();
    }
}
