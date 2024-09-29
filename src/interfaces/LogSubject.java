package interfaces;

/**
 * Subject class to inform corresponding observer for the log updates. Follows
 * the Observer pattern.
 */
public interface LogSubject {
    /**
     * Attach a LogObserver in order to delegate to him the updated on the log
     * that will follow.
     *
     * @param logObserver the observer to attach to LogSubject state.
     */
    void attachLogObserver(LogObserver logObserver);

    /**
     * Update log observer with the string to be appended on the log.
     *
     * @param log the string to be appended to the game transcript.
     */
    void notifyLogObserver(String log);
}
