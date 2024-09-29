package interfaces;

/**
 * Subject class to inform corresponding observer for the player's stats updates.
 * Follows the Observer pattern.
 */
public interface StatSubject {
    /**
     * Attach a StatObserver in order to delegate to him the updated on the player's
     * stats that will follow.
     *
     * @param statObserver the observer to attack to StatSubject state
     */
    void attachStatObserver(StatObserver statObserver);

    /**
     * Notify the StatObserver that player's stats need to be renewed.
     */
    void notifyStatObserver();
}
