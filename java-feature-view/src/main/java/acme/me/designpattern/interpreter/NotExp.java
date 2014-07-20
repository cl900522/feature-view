package acme.me.designpattern.interpreter;

public class NotExp implements BooleanExp {
    private BooleanExp op1;

    public NotExp(BooleanExp a) {
        this.op1 = a;
    }

    public boolean evaluate(Context c) {
        return !op1.evaluate(c);
    }

    public BooleanExp replace(String c, BooleanExp e) {
        return new NotExp(op1.replace(c, e));
    }

    public BooleanExp copy() {
        return new NotExp(op1.copy());
    }

}
