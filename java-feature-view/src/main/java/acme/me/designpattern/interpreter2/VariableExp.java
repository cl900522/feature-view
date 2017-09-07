package acme.me.designpattern.interpreter2;

public class VariableExp implements CalculateExp {
    private String str;
    public VariableExp(String str){
        this.str = str;
    }
    public Double evaluate(Context<Double> c) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            //System.out.println(str + " can not be parsed to double!");
            return c.lookup(str);
        }
    }

}
