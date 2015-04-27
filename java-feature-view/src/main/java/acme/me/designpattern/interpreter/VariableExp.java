package acme.me.designpattern.interpreter;

public class VariableExp implements BooleanExp {
    private String name;
    public String getName(){
        return name;
    }
    public VariableExp(String exp) {
        this.name = exp;
    }

    public boolean evaluate(Context c) {
        return c.lookup(name);
    }

    public BooleanExp replace(String c, BooleanExp e) {
        if(name.equals(c)){
            return e.copy();
        }else{
            return new VariableExp(name);
        }
    }

    public BooleanExp copy() {
        return new VariableExp(name);
    }

}
