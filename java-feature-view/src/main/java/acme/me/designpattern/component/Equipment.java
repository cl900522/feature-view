package acme.me.designpattern.component;

import java.util.Iterator;

public abstract class Equipment {
    private String name;
    private Currency currency;
    private Watt watt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Watt getWatt() {
        return watt;
    }

    public void setWatt(Watt watt) {
        this.watt = watt;
    }

    public Currency getTotalCurrency() {
        return this.getCurrency();
    }

    public abstract CompositeEquipment getComposite();

    public abstract void add(Equipment left);

    public abstract void remove(Equipment left);

    public abstract Iterator<Equipment> createIterator();
}
