package acme.me.designpattern.interpreter2;

import java.util.Stack;

public class InterpreterView {
    /**
     * a 运算符是否优先于b运算符
     */
    public static boolean precedence(char a, char b) {
        String high = "*/", low = "+-";
        if (a == '(')
            return false;
        if (a == ')' && b == '(') {
            System.out.println(")-(");
            return false;
        }
        if (b == '(')
            return false;
        if (b == ')')
            return true;
        if (high.indexOf(a) > -1 && low.indexOf(b) > -1)
            return true;
        if (high.indexOf(a) > -1 && high.indexOf(b) > -1)
            return true;
        if (low.indexOf(a) > -1 && low.indexOf(b) > -1)
            return true;
        return false;
    }

    /**
     * 将运算表达式解析为运算顺序栈
     */
    public static Stack<String> parse2Stack(String expression) {
        char[] tokens = expression.replace(" ", "").toCharArray();
        String opers = "()+-*/";
        Stack<Character> op_stack = new Stack<Character>();
        char top_sym = '+';
        boolean empty;

        Stack<String> all_stack = new Stack<String>();
        String singleUnit = "";
        for (int i = 0; i < tokens.length; i++) {
            singleUnit += tokens[i];
            if (opers.indexOf(tokens[i]) == -1) {
                /* 非运算符 */
                if ((i + 1 >= tokens.length || opers.indexOf(tokens[i + 1]) != -1)) {
                    all_stack.push(singleUnit);
                    singleUnit = "";
                }
            } else {
                /* 运算符 */
                while (!(empty = op_stack.isEmpty()) && precedence(top_sym = op_stack.pop(), tokens[i])) {
                    all_stack.push(top_sym + "");
                }
                if (!empty)
                    op_stack.push(top_sym);
                if (empty || tokens[i] != ')')
                    op_stack.push(tokens[i]);
                else
                    top_sym = op_stack.pop();

                singleUnit = "";
            }
        }
        while (!op_stack.isEmpty()) {
            all_stack.add(op_stack.pop() + "");
        }
        return all_stack;
    }

    /**
     * 将运算栈转换为解析式模式
     */
    public static CalculateExp exchagne(Stack<String> all_stack) {
        String opers = "()+-*/";
        CalculateExp result = null;
        if (!all_stack.empty()) {
            String pop = all_stack.pop();
            if (opers.indexOf(pop) != -1) {
                CalculateExp a = exchagne(all_stack);
                CalculateExp b = exchagne(all_stack);
                if (pop.equals("+")) {
                    result = new PlusExp(b, a);
                }
                if (pop.equals("-")) {
                    result = new MinusExp(b, a);
                }
                if (pop.equals("*")) {
                    result = new MultiplyExp(b, a);
                }
                if (pop.equals("/")) {
                    result = new DivideExp(b, a);
                }
            } else {
                result = new VariableExp(pop);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Context<Double> context = new Context<Double>();
        context.assign("m", 9d);
        context.assign("n", 3d);

        CalculateExp a = new VariableExp("5");
        CalculateExp b = new VariableExp("m");
        CalculateExp exp = new MinusExp(a, b);
        System.out.println(exp.evaluate(context));

        Stack<String> st = parse2Stack("((1 8/6 + 4) +3*4 *6-15 )/(n+1-2)");
        System.out.println(st);
        exp = exchagne(st);
        if (exp != null)
            System.out.println(exp.evaluate(context));

    }
}
