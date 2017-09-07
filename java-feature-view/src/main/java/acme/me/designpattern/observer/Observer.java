package acme.me.designpattern.observer;

public interface Observer {
    void update();
    void setSubject(Subject s);
}
