package acme.me.designpattern.iterator;

public interface Aggregrate<T> {
    public Iterator<T> createIterator();
}
