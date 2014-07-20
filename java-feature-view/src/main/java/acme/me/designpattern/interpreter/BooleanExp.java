package acme.me.designpattern.interpreter;

public interface BooleanExp {
    public boolean evaluate(Context c);
    public BooleanExp replace(String c, BooleanExp e);
    public BooleanExp copy();
}
