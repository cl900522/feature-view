package acme.me.designpattern.interpreter2;

public class DivideExp implements CalculateExp {
    private CalculateExp a;
    private CalculateExp b;

    public DivideExp(CalculateExp a, CalculateExp b) {
        this.a = a;
        this.b = b;
    }

    public Double evaluate(Context<Double> c) {
        return a.evaluate(c) / b.evaluate(c);
    }
}
