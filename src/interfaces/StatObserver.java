package interfaces;

/**
 * Observer class for the player's stats. Follows the Observer design pattern.
 */
public interface StatObserver {
    /**
     * Update player's state on the information panel.
     */
    void updateStats();
}
