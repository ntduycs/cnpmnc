package observer;

import java.util.ArrayList;
import java.util.List;

public interface Observable {

    List<Observer> lstObserver = new ArrayList<>();

    default void notifyAllObservers() {
        for (Observer o: lstObserver) {
            o.update(null);
        }
    }

    default void notifyAllObservers(Object data) {
        for (Observer o: lstObserver) {
            o.update(data);
        }
    }

    default void attachObserver(Observer o) {
        lstObserver.add(o);
    }

    default void detachObserver(Observer o) {
        lstObserver.remove(o);
    }

}
