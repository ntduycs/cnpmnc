package observer;

import java.util.ArrayList;
import java.util.List;

public interface Observable {

    List<Observer> lstObserver = new ArrayList<>();

    void notifyAllObservers();

    default void attachObserver(Observer o) {
        lstObserver.add(o);
    }

    default void detachObserver(Observer o) {
        lstObserver.remove(o);
    }

}
