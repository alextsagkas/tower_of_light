package interfaces;

/**
 * Tagging interface that signals the ability to restart the state of
 * the class that implements it.
 */
public interface Restartable {
    /**
     * Restart the state of the component.
     */
    void restart();
}
