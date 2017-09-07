package acme.me.designpattern.interpreter;

public class AndExp implements BooleanExp {
    private BooleanExp op1;
    private BooleanExp op2;

    public AndExp(BooleanExp a, BooleanExp b) {
        this.op1 = a;
        this.op2 = b;
    }

    public boolean evaluate(Context c) {
        return op1.evaluate(c) && op2.evaluate(c);
    }

    public BooleanExp replace(String c, BooleanExp e) {
        return new AndExp(op1.replace(c, e), op2.replace(c, e));
    }

    public BooleanExp copy() {
        return new AndExp(op1.copy(), op2.copy());
    }

}
