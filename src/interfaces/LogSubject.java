package interfaces;

public interface LogSubject {
    void attachLogObserver(LogObserver logObserver);

    void notifyLogObserver(String log);
}
