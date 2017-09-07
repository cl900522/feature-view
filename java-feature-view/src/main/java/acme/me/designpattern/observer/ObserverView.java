package acme.me.designpattern.observer;

public class ObserverView {
    public static void main(String[] args) {
        Subject s = new ConcreteSubject();
        s.attach(new ConcreteObserver1());
        s.attach(new ConcreteObserver2());
        ((ConcreteSubject) s).setKey("Combo!");
    }
}
