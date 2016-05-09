package acme.me.designpattern.proxy;

public interface Subject {
    public void doSomeThing() throws Exception;

    public String getName();

    public void throwException() throws Exception;
}
