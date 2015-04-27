package acme.me.designpattern.iterator;

public interface Iterator<T> {
    public void first();
    public void next();
    public boolean isDone();
    public T currentItem();
}
