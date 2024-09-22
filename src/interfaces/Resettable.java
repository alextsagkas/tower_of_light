package interfaces;

public interface Resettable extends Restartable {
    void reset();

    @Override
    default void restart() {
        reset();
    }
}
