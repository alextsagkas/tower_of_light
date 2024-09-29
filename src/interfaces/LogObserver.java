package interfaces;

/**
 * Observer class for the game transcript. Follows the Observer design pattern.
 */
public interface LogObserver {
    /**
     * Provide the LogObserver with the specific log to print on the transcript.
     *
     * @param log the log to be printed on the transcript.
     */
    void updateLog(String log);
}
