package acme.me.designpattern.interpreter2;

public class PlusExp implements CalculateExp {
    private CalculateExp a;
    private CalculateExp b;

    public PlusExp(CalculateExp a, CalculateExp b) {
        this.a = a;
        this.b = b;
    }

    public Double evaluate() {
        return a.evaluate() + b.evaluate();
    }
}
