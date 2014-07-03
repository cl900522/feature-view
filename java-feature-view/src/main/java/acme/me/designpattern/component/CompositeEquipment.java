package acme.me.designpattern.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class CompositeEquipment extends Equipment {
    private List<Equipment> equipments = new ArrayList<Equipment>();

    public CompositeEquipment getComposite() {
        return this;
    }

    public void add(Equipment component) {
        this.equipments.add(component);
    }

    @Override
    public void remove(Equipment left) {
        this.equipments.add(left);
    }

    @Override
    public Iterator<Equipment> createIterator() {
        return equipments.iterator();
    }

    public Currency getTotalCurrency() {
        Iterator<Equipment> ite = this.createIterator();
        Equipment node;
        double total = this.getCurrency().getCount();
        while (ite.hasNext()) {
            node = ite.next();
            total += node.getCurrency().getCount();
        }
        return new Currency(total, "$");
    }
}
