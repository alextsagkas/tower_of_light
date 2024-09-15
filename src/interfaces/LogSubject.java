package interfaces;

public interface LogSubject {
    void attach(LogObserver logObserver);

    void notifyObserver(String log);
}
