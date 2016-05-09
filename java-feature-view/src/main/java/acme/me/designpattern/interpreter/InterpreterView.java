package acme.me.designpattern.interpreter;

public class InterpreterView {
    VariableExp x = new VariableExp("X");
    VariableExp y = new VariableExp("Y");
    BooleanExp aexp = new AndExp(x, y);
    BooleanExp oexp = new OrExp(x, y);
    BooleanExp notexp = new NotExp(x);

    public static void main(String[] args) {
        InterpreterView view = new InterpreterView();
        Context t = new Context();
        t.assign(view.x, true);
        t.assign(view.y, false);
        view.getResult(t);

        t.assign(view.x, true);
        t.assign(view.y, true);
        view.getResult(t);

        t.assign(view.x, false);
        t.assign(view.y, false);
        view.getResult(t);
    }

    public void getResult(Context t) {
        System.out.println("*************************************************");
        System.out.println("X is " + t.lookup("X") + ";Y is " + t.lookup("Y"));
        System.out.println("X And Y operation result is: " + this.aexp.evaluate(t));
        System.out.println("X Or Y operation result is: " + this.oexp.evaluate(t));
        System.out.println("Not X operation result is: " + this.notexp.evaluate(t));
    }
}
