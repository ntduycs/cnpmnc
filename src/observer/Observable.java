package observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private List<Observer> lstObserver = new ArrayList<>();

    public void notifyAllObservers() {
        for (Observer o: lstObserver) {
            o.update(null);
        }
    }

    public void notifyAllObservers(Object data) {
        for (Observer o: lstObserver) {
            o.update(data);
        }
    }

    public void attachObserver(Observer o) {
        lstObserver.add(o);
    }

    public void detachObserver(Observer o) {
        lstObserver.remove(o);
    }

}
