package acme.me.designpattern.component;

public class Watt {
    private double watt;
    private String unit;

    public Watt(double watt, String unit) {
        this.watt = watt;
        this.unit = unit;
    }

    public double getWatt() {
        return watt;
    }

    public void setWatt(double watt) {
        this.watt = watt;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String toString() {
        return this.getWatt() + "(" + this.getUnit() + ")";
    }
}
