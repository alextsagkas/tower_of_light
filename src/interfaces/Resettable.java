package interfaces;

/**
 * Tagging interface that signals the ability to reset (and also restart) the
 * state of the class that implements it.
 */
public interface Resettable extends Restartable {
    /**
     * Reset the state of the component.
     */
    void reset();

    /**
     * Restart the state of the component.
     */
    @Override
    default void restart() {
        reset();
    }
}
