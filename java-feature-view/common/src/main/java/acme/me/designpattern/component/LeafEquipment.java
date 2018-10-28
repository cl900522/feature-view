package acme.me.designpattern.component;

import java.util.Iterator;

public abstract class LeafEquipment extends Equipment {

    public CompositeEquipment getComposite() {
        return null;
    }

    @Override
    public void add(Equipment left) {
    }

    @Override
    public void remove(Equipment left) {
    }

    @Override
    public Iterator<Equipment> createIterator() {
        return null;
    }
}
