package acme.me.designpattern.component;

public class Currency {
    private double count;
    private String unit;

    public Currency(double count, String unit) {
        this.count = count;
        this.unit = unit;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double watt) {
        this.count = watt;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String toString() {
        return this.getCount() + "(" + this.getUnit() + ")";
    }
}
