package acme.me.designpattern.interpreter;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, Boolean> list = new HashMap<String, Boolean>();

    boolean lookup(String c) {
        return list.get(c);
    }

    public void assign(VariableExp e, Boolean b) {
        list.put(e.getName(), b);
    }
}
