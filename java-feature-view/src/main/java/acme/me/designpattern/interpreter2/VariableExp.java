package acme.me.designpattern.interpreter2;

public class VariableExp implements CalculateExp {
    private String str;

    public Double evaluate() {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

}
