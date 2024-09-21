package interfaces;

public interface StatSubject {
    void attachStatObserver(StatObserver statObserver);

    void notifyStatObserver();
}
