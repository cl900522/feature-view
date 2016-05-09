package acme.me.designpattern.interpreter2;

public class MinusExp implements CalculateExp {
    private CalculateExp a;
    private CalculateExp b;

    public MinusExp(CalculateExp a, CalculateExp b) {
        this.a = a;
        this.b = b;
    }

    public Double evaluate(Context<Double> c) {
        return a.evaluate(c) - b.evaluate(c);
    }
}
