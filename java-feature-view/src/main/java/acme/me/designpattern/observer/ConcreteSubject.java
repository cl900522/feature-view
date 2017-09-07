package acme.me.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSubject implements Subject {
    private List<Observer> observerList = new ArrayList<Observer>();
    private String key;

    public void attach(Observer o) {
        observerList.add(o);
        o.setSubject(this);
    }

    public void detach(Observer o) {
        observerList.remove(o);
    }

    public void notifyObserver() {
        for (Observer o : observerList) {
            o.update();
        }
    }

    public String getKey(){
        return key;
    }
    public void setKey(String key){
        this.key = key;
        notifyObserver();
    }
}
