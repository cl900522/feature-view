package acme.me.designpattern.observer;

public class ConcreteObserver1 implements Observer {
    private Subject s;

    public void update() {
        String key = ((ConcreteSubject)s).getKey();
        System.out.println("#观察者1获取数据#"+key+"##");
    }

    public void setSubject(Subject s) {
        this.s = s;
    }

}
