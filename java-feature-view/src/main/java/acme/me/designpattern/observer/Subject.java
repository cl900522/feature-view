package acme.me.designpattern.observer;

public interface Subject {
    public void attach(Observer o);
    public void detach(Observer o);
    public void notifyObserver();
    public String getKey();
}
